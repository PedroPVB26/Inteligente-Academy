package br.edu.utfpr.inteligenteacademy.service;

import br.edu.utfpr.inteligenteacademy.entity.Course;
import br.edu.utfpr.inteligenteacademy.entity.Enrollment;
import br.edu.utfpr.inteligenteacademy.entity.Lesson;
import br.edu.utfpr.inteligenteacademy.entity.LessonProgress;
import br.edu.utfpr.inteligenteacademy.entity.User;
import br.edu.utfpr.inteligenteacademy.exception.BadRequestException;
import br.edu.utfpr.inteligenteacademy.model.PublicationStatus;
import br.edu.utfpr.inteligenteacademy.model.dto.progress.LessonProgressResponseDto;
import br.edu.utfpr.inteligenteacademy.model.dto.progress.LessonProgressUpdateRequestDto;
import br.edu.utfpr.inteligenteacademy.model.event.LessonProgressUpdatedEvent;
import br.edu.utfpr.inteligenteacademy.repository.LessonProgressRepository;
import br.edu.utfpr.inteligenteacademy.repository.LessonRepository;
import br.edu.utfpr.inteligenteacademy.model.dto.progress.CourseProgressResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@AllArgsConstructor
public class LessonProgressService {

    private final LessonProgressRepository lessonProgressRepository;
    private final LessonService lessonService;
    private final EnrollmentService enrollmentService;
    private final LessonRepository lessonRepository;
    private final CourseService courseService;
    private final ApplicationEventPublisher  applicationEventPublisher;

    @Transactional
    public LessonProgressResponseDto updateProgress(LessonProgressUpdateRequestDto dto, User currentUser) {
        LessonProgress lessonProgress = getOrCreateProgress(currentUser, dto.lessonId());

        long durationInSeconds = lessonProgress.getLesson().getDurationInSeconds();
        long normalizedPosition = Math.min(dto.highestPositionReachedSeconds(), durationInSeconds);

        // Nunca deixa o progresso regredir e também nunca passa da duração da aula.
        lessonProgress.setHighestPositionReachedSeconds(Math.max(lessonProgress.getHighestPositionReachedSeconds(), normalizedPosition));

        Instant now = Instant.now();
        lessonProgress.setLastAccessedAt(now);

        double completionPercentage = (lessonProgress.getHighestPositionReachedSeconds() * 100.0) / durationInSeconds;

        // Se chegou no mínimo exigido, marca como concluída.
        if (completionPercentage >= 95.0 && !lessonProgress.getCompleted()) {
            lessonProgress.setCompleted(true);
            lessonProgress.setCompletedAt(now);
        }

        LessonProgress savedLessonProgress = lessonProgressRepository.save(lessonProgress);

        applicationEventPublisher.publishEvent(
                new LessonProgressUpdatedEvent(savedLessonProgress.getEnrollment().getId())
        );

        return new LessonProgressResponseDto(
                savedLessonProgress.getEnrollment().getId(),
                savedLessonProgress.getLesson().getId(),
                savedLessonProgress.getHighestPositionReachedSeconds(),
                savedLessonProgress.getCompleted(),
                Math.min(completionPercentage, 100.0)
        );
    }

    @Transactional(readOnly = true)
    public CourseProgressResponseDto getCourseProgress(User user, Long courseId) {
        // Valida se o curso existe
        var course = courseService.findEntityById(courseId);

        // Verifica se o usuário está matriculado no curso (lança ResourceNotFoundException se não)
        var enrollment = enrollmentService.findByUserAndCourse(user.getId(), courseId);

        long totalLessons = lessonRepository.countByCourseModuleCourseId(courseId);

        long completedLessons = lessonProgressRepository.countByEnrollmentIdAndCompletedTrue(enrollment.getId());

        double progress = 0.0;
        if (totalLessons > 0) {
            progress = (double) completedLessons / (double) totalLessons;
        }

        return new CourseProgressResponseDto(
                user.getId(),
                user.getFullName(),
                course.getId(),
                course.getName(),
                progress
        );
    }

    private LessonProgress getOrCreateProgress(User user, Long lessonId) {
        return lessonProgressRepository.findByEnrollmentUserIdAndLessonId(user.getId(), lessonId).orElseGet(() -> createProgress(user, lessonId));
    }

    private LessonProgress createProgress(User user, Long lessonId) {
        Lesson lesson = lessonService.findEntityById(lessonId);

        if (lesson.getPublicationStatus() != PublicationStatus.PUBLISHED) {
            throw new BadRequestException("Lesson is not published");
        }

        Course course = lesson.getCourseModule().getCourse();
        Enrollment enrollment = enrollmentService.findByUserAndCourse(user.getId(), course.getId());

        Instant now = Instant.now();

        LessonProgress lessonProgress = new LessonProgress();
        lessonProgress.setEnrollment(enrollment);
        lessonProgress.setLesson(lesson);
        lessonProgress.setHighestPositionReachedSeconds(0L);
        lessonProgress.setCompleted(false);
        lessonProgress.setStartedAt(now);
        lessonProgress.setLastAccessedAt(now);

        return lessonProgressRepository.save(lessonProgress);
    }
}