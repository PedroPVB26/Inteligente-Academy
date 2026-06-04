package br.edu.utfpr.inteligenteacademy.service;

import br.edu.utfpr.inteligenteacademy.entity.Course;
import br.edu.utfpr.inteligenteacademy.entity.EnrollmentRequest;
import br.edu.utfpr.inteligenteacademy.entity.User;
import br.edu.utfpr.inteligenteacademy.exception.BadRequestException;
import br.edu.utfpr.inteligenteacademy.exception.DatabaseException;
import br.edu.utfpr.inteligenteacademy.exception.ResourceNotFoundException;
import br.edu.utfpr.inteligenteacademy.model.EnrollmentRequestStatus;
import br.edu.utfpr.inteligenteacademy.model.dto.enrollment.EnrollmentRequestResponseDto;
import br.edu.utfpr.inteligenteacademy.repository.EnrollmentRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentRequestService {

    private final EnrollmentRequestRepository enrollmentRequestRepository;
    private final EnrollmentService enrollmentService;
    private final UserService userService;
    private final CourseService courseService;

    private EnrollmentRequest findEntityById(Long requestId){
        return enrollmentRequestRepository.findById(requestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Enrollment request with id " + requestId + " not found"
                        ));
    }

    public boolean existsPendingRequest(
            Long userId,
            Long courseId
    ) {
        return enrollmentRequestRepository
                .existsByUserIdAndCourseIdAndStatus(
                        userId,
                        courseId,
                        EnrollmentRequestStatus.PENDING
                );
    }

    @Transactional
    public EnrollmentRequestResponseDto createEnrollmentRequest(Long userId, Long courseId){
        if (enrollmentService.existsByUserIdAndCourseId(userId, courseId)) {
            throw new DatabaseException(
                    "User with id " + userId +
                            " is already enrolled in course with id " + courseId
            );
        }

        if (existsPendingRequest(userId, courseId)) {
            throw new DatabaseException(
                    "User with id " + userId + " already has a pending request for the course with id " + courseId
            );
        }

        User user = userService.findEntityById(userId);
        Course course = courseService.findEntityById(courseId);

        EnrollmentRequest enrollmentRequest = new EnrollmentRequest();

        enrollmentRequest.setUser(user);
        enrollmentRequest.setCourse(course);
        enrollmentRequest.setStatus(EnrollmentRequestStatus.PENDING);

        EnrollmentRequest enrollmentRequestSaved = enrollmentRequestRepository.save(enrollmentRequest);

        return new EnrollmentRequestResponseDto(enrollmentRequestSaved);
    }

    public EnrollmentRequestResponseDto findById(Long requestId) {
        return new EnrollmentRequestResponseDto(
                findEntityById(requestId)
        );
    }

    public List<EnrollmentRequestResponseDto> findAll() {
        return enrollmentRequestRepository.findAll()
                .stream()
                .map(EnrollmentRequestResponseDto::new)
                .toList();
    }

    public List<EnrollmentRequestResponseDto> findPending() {
        return enrollmentRequestRepository
                .findByStatus(EnrollmentRequestStatus.PENDING)
                .stream()
                .map(EnrollmentRequestResponseDto::new)
                .toList();
    }


    @Transactional
    public EnrollmentRequestResponseDto approve(Long requestId){
        EnrollmentRequest enrollmentRequest = findEntityById(requestId);

        if(enrollmentRequest.getStatus() != EnrollmentRequestStatus.PENDING) {
            throw new BadRequestException("Only pending requests can be approved");
        }

        enrollmentService.enrollUser(enrollmentRequest.getUser(), enrollmentRequest.getCourse());

        enrollmentRequest.setStatus(EnrollmentRequestStatus.APPROVED);

        EnrollmentRequest enrollmentRequestSaved = enrollmentRequestRepository.save(enrollmentRequest);

        return new EnrollmentRequestResponseDto(enrollmentRequestSaved);
    }

    @Transactional
    public EnrollmentRequestResponseDto reject(Long requestId){
        EnrollmentRequest enrollmentRequest = findEntityById(requestId);

        if(enrollmentRequest.getStatus() != EnrollmentRequestStatus.PENDING) {
            throw new BadRequestException("Only pending requests can be rejected");
        }

        enrollmentRequest.setStatus(EnrollmentRequestStatus.REJECTED);

        EnrollmentRequest enrollmentRequestSaved = enrollmentRequestRepository.save(enrollmentRequest);

        return new EnrollmentRequestResponseDto(enrollmentRequestSaved);
    }
}
