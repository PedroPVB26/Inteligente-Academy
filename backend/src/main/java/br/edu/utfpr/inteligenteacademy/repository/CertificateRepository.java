package br.edu.utfpr.inteligenteacademy.repository;

import br.edu.utfpr.inteligenteacademy.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    boolean existsByEnrollmentId(
            Long enrollmentId
    );

    Optional<Certificate> findByEnrollmentId(Long enrollmentId);

    Optional<Certificate> findByValidationCode(String validationCode);
}