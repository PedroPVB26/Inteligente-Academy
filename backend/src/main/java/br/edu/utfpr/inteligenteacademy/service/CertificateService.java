package br.edu.utfpr.inteligenteacademy.service;

import br.edu.utfpr.inteligenteacademy.entity.Certificate;
import br.edu.utfpr.inteligenteacademy.entity.Enrollment;
import br.edu.utfpr.inteligenteacademy.exception.ResourceNotFoundException;
import br.edu.utfpr.inteligenteacademy.model.CertificateFile;
import br.edu.utfpr.inteligenteacademy.model.dto.certificate.CertificateValidationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.certificate.CertificateViewDto;
import br.edu.utfpr.inteligenteacademy.repository.CertificateRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CertificateService {
    private final CertificateRepository certificateRepository;
    private final EnrollmentService enrollmentService;
    private final PdfGenerator pdfGenerator;
    private final FileStorageService  fileStorageService;

    @Transactional
    public void generateCertificate(Long enrollmentId){

        Enrollment enrollment = enrollmentService.findEntityById(enrollmentId);

        // Já tem um certificado
        if(certificateRepository.existsByEnrollmentId(enrollmentId)){
            return;
        }

        Certificate certificate = new Certificate();

        certificate.setEnrollment(enrollment);
        certificate.setIssuedAt(Instant.now());
        certificate.setValidationCode(UUID.randomUUID().toString());

        byte[] pdf = pdfGenerator.generatePdf(enrollment.getId());
        String pdfUrl = fileStorageService.save(pdf, certificate.getValidationCode());
        certificate.setPdfUrl(pdfUrl);

        certificateRepository.save(certificate);
    }

    @Transactional(readOnly = true)
    Certificate findEntityByEnrollmentId(Long enrollmentId) {
        return certificateRepository.findByEnrollmentId(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Certificate for enrollment id " + enrollmentId + " not found."
                ));
    }

    @Transactional(readOnly = true)
    public CertificateFile downloadCertificate(Long enrollmentId, Long userId) {
        Enrollment enrollment = enrollmentService.findEntityById(enrollmentId);

        if(!enrollment.getUser().getId().equals(userId)){
            throw new AccessDeniedException("You do not have permission to access this certificate.");
        }

        Certificate certificate = findEntityByEnrollmentId(enrollmentId);

        String fileName =
                "Certificado_"
                        + enrollment.getCourse()
                        .getName()
                        .replaceAll("[^a-zA-Z0-9\\s]", "")
                        .replace(" ", "_")
                        + "_"
                        + enrollment.getUser()
                        .getFullName()
                        .replaceAll("[^a-zA-Z0-9\\s]", "")
                        .replace(" ", "_")
                        + ".pdf";


        return new CertificateFile(
                fileStorageService.load(certificate.getPdfUrl()),
                fileName
        );
    }

    @Transactional(readOnly = true)
    public CertificateValidationDto validateByValidationCode(String validationCode) {
        CertificateViewDto certificateViewDto = findEntityByValidationCode(validationCode);

        return new CertificateValidationDto(
                true,
                "Valid certificate",
                certificateViewDto
        );
    }

    @Transactional(readOnly = true)
    public CertificateViewDto findEntityByValidationCode(String validationCode) {
        Certificate certificate =  certificateRepository.findByValidationCode(validationCode)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Certificate with validation code '" + validationCode + "' not found."
                ));

        return new CertificateViewDto(certificate);
    }
}
