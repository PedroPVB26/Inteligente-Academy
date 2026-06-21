package br.edu.utfpr.inteligenteacademy.service;

import br.edu.utfpr.inteligenteacademy.entity.Course;
import br.edu.utfpr.inteligenteacademy.entity.Enrollment;
import br.edu.utfpr.inteligenteacademy.entity.User;
import br.edu.utfpr.inteligenteacademy.exception.DatabaseException;
import br.edu.utfpr.inteligenteacademy.exception.ResourceNotFoundException;
import br.edu.utfpr.inteligenteacademy.model.EnrollmentStatus;
import br.edu.utfpr.inteligenteacademy.model.dto.enrollment.EnrollmentResponseDto;
import br.edu.utfpr.inteligenteacademy.model.event.CourseCompletedEvent;
import br.edu.utfpr.inteligenteacademy.repository.CourseRepository;
import br.edu.utfpr.inteligenteacademy.repository.EnrollmentRepository;
import br.edu.utfpr.inteligenteacademy.repository.LessonProgressRepository;
import br.edu.utfpr.inteligenteacademy.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final UserService userService;
    private final CourseService courseService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final LessonProgressRepository  lessonProgressRepository;
    private final LessonRepository lessonRepository;

    Enrollment findEntityById(Long enrollmentId) {
        return enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Enrollment with id " + enrollmentId + " not found"
                        ));
    }

    Enrollment findByUserAndCourse(Long userId, Long courseId) {
        return enrollmentRepository.findByUserIdAndCourseId(userId, courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Enrollment for user id " + userId + " and course id " + courseId + " not found"
                        ));
    }

//    public EnrollmentResponseDto findByUserAndCourse(Long userId, Long courseId) {
//        Enrollment enrollment =  enrollmentRepository.findByUserIdAndCourseId(userId, courseId)
//                .orElseThrow(() ->
//                        new ResourceNotFoundException(
//                                "Enrollment for user id " + userId + " and course id " + courseId + " not found"
//                        ));
//
//        return new EnrollmentResponseDto(enrollment);
//    }

    public boolean existsByUserIdAndCourseId(Long userId, Long courseId) {
        return enrollmentRepository.existsByUserIdAndCourseId(userId, courseId);
    }

    @Transactional
    public void evaluateCourseCompletion(Long enrollmentId) {
        Enrollment enrollment = findEntityById(enrollmentId);

        if(enrollment.getStatus() == EnrollmentStatus.COMPLETED) {
            applicationEventPublisher.publishEvent(new CourseCompletedEvent(enrollmentId));
            return;
        }

        Long courseId = enrollment.getCourse().getId();

        long totalLessons = lessonRepository.countPublishedLessonsByCourse(courseId);

        long completedLessons = lessonProgressRepository.countCompletedLessonsByEnrollmentAndCourse(
                enrollmentId, courseId
        );

        double completedPercentage =
                totalLessons == 0
                        ? 0
                        : ((double)completedLessons / (double)totalLessons) * 100;

        enrollment.setProgressPercentage(completedPercentage);

        if(completedLessons == totalLessons && totalLessons > 0){
            enrollment.setStatus(EnrollmentStatus.COMPLETED);
            enrollment.setCompletedAt(Instant.now());

            applicationEventPublisher.publishEvent(new CourseCompletedEvent(enrollmentId));
        }

        enrollmentRepository.save(enrollment);
    }

    @Transactional
    public EnrollmentResponseDto enrollUser(User user, Course  course) {
        if(existsByUserIdAndCourseId(user.getId(), course.getId())) {
            throw new DatabaseException("User with id " + user.getId() + " is already enrolled in the course with id " + course.getId());
        }

        Enrollment enrollment = new Enrollment();

        enrollment.setUser(user);
        enrollment.setCourse(course);
        enrollment.setStatus(EnrollmentStatus.ACTIVE);
        enrollment.setProgressPercentage(0.0);

        Enrollment savedEnrollment =  enrollmentRepository.save(enrollment);

        return new EnrollmentResponseDto(savedEnrollment);
    }

    public EnrollmentResponseDto findById(Long id) {
        return new EnrollmentResponseDto(
                findEntityById(id)
        );
    }

    public List<EnrollmentResponseDto> findAll() {
        return enrollmentRepository.findAll()
                .stream()
                .map(EnrollmentResponseDto::new)
                .toList();
    }

    public List<EnrollmentResponseDto> findByUser(Long userId) {
        userService.findById(userId);

        return enrollmentRepository.findByUserId(userId)
                .stream()
                .map(EnrollmentResponseDto::new)
                .toList();
    }

    public List<EnrollmentResponseDto> findByCourse(Long courseId) {
        courseService.findById(courseId);

        return enrollmentRepository.findByCourseId(courseId)
                .stream()
                .map(EnrollmentResponseDto::new)
                .toList();
    }

}
