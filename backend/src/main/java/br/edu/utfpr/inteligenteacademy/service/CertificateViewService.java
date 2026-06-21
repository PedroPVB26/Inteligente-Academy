package br.edu.utfpr.inteligenteacademy.service;

import br.edu.utfpr.inteligenteacademy.entity.Certificate;
import br.edu.utfpr.inteligenteacademy.entity.Enrollment;
import br.edu.utfpr.inteligenteacademy.model.dto.certificate.CertificateViewDto;
import br.edu.utfpr.inteligenteacademy.repository.CertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CertificateViewService {
    private final EnrollmentService enrollmentService;
    private final CertificateRepository certificateRepository;

    public CertificateViewDto getCertificateData(Long enrollmentId) {
        enrollmentService.findEntityById(enrollmentId);

        Certificate certificate = certificateRepository
                .findByEnrollmentId(enrollmentId)
                .orElseThrow();

        return new CertificateViewDto(certificate);
    }
}