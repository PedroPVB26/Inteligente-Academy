package br.edu.utfpr.inteligenteacademy.model.dto.enrollment;

import br.edu.utfpr.inteligenteacademy.entity.EnrollmentRequest;
import br.edu.utfpr.inteligenteacademy.model.EnrollmentRequestStatus;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.Instant;

@JsonPropertyOrder({
        "id",
        "userId",
        "userName",
        "courseId",
        "courseName",
        "requestedAt",
        "status"
})
public record EnrollmentRequestResponseDto(
        Long id,
        Long userId,
        String userName,
        Long courseId,
        String courseName,
        Instant requestedAt,
        EnrollmentRequestStatus status
) {

    public EnrollmentRequestResponseDto(
            EnrollmentRequest enrollmentRequest
    ) {
        this(
                enrollmentRequest.getId(),
                enrollmentRequest.getUser().getId(),
                enrollmentRequest.getUser().getUsername(),
                enrollmentRequest.getCourse().getId(),
                enrollmentRequest.getCourse().getName(),
                enrollmentRequest.getRequestedAt(),
                enrollmentRequest.getStatus()
        );
    }
}