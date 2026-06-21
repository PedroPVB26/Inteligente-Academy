package br.edu.utfpr.inteligenteacademy.model.dto.enrollment;

import br.edu.utfpr.inteligenteacademy.entity.Enrollment;
import br.edu.utfpr.inteligenteacademy.model.EnrollmentStatus;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.Instant;

@JsonPropertyOrder({
        "id",
        "userId",
        "userName",
        "courseId",
        "courseName",
        "enrolledAt",
        "completedAt",
        "status",
        "progressPercentage"
})
public record EnrollmentResponseDto(
        Long id,
        Long userId,
        String userName,
        Long courseId,
        String courseName,
        Instant enrolledAt,
        Instant completedAt,
        EnrollmentStatus status,
        Double progressPercentage
) {

    public EnrollmentResponseDto(Enrollment enrollment) {
        this(
                enrollment.getId(),
                enrollment.getUser().getId(),
                enrollment.getUser().getUsername(),
                enrollment.getCourse().getId(),
                enrollment.getCourse().getName(),
                enrollment.getEnrolledAt(),
                enrollment.getCompletedAt(),
                enrollment.getStatus(),
                enrollment.getProgressPercentage()
        );
    }
}