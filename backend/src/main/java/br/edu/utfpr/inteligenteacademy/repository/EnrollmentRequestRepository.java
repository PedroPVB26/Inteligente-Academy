package br.edu.utfpr.inteligenteacademy.repository;

import br.edu.utfpr.inteligenteacademy.entity.EnrollmentRequest;
import br.edu.utfpr.inteligenteacademy.model.EnrollmentRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRequestRepository extends JpaRepository<EnrollmentRequest, Long> {
    Optional<EnrollmentRequest> findByUserIdAndCourseId(
            Long userId,
            Long courseId
    );

    List<EnrollmentRequest> findByStatus(
            EnrollmentRequestStatus status
    );

    boolean existsByUserIdAndCourseId(
            Long userId,
            Long courseId
    );

    boolean existsByUserIdAndCourseIdAndStatus(
            Long userId,
            Long courseId,
            EnrollmentRequestStatus status
    );
}
