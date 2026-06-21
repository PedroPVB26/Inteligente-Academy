package br.edu.utfpr.inteligenteacademy.model.dto.certificate;

import br.edu.utfpr.inteligenteacademy.entity.Certificate;
import br.edu.utfpr.inteligenteacademy.entity.Enrollment;
import lombok.Getter;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Getter
public class CertificateViewDto {

    private static final ZoneId ZONE_ID =
            ZoneId.of("America/Sao_Paulo");

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    .withLocale(Locale.of("pt", "BR"));

    private final String studentName;
    private final String courseName;
    private final Long workload;
    private final String startDate;
    private final String endDate;
    private final String issueDate;
    private final String validationCode;

    public CertificateViewDto(Certificate certificate) {

        Enrollment enrollment = certificate.getEnrollment();

        this.studentName = enrollment.getUser().getFullName();
        this.courseName = enrollment.getCourse().getName();
        this.workload = enrollment.getCourse().getDurationInSeconds() / 3600;

        this.startDate = enrollment.getEnrolledAt()
                .atZone(ZONE_ID)
                .toLocalDate()
                .format(DATE_FORMATTER);

        this.endDate = enrollment.getCompletedAt()
                .atZone(ZONE_ID)
                .toLocalDate()
                .format(DATE_FORMATTER);

        this.issueDate = certificate.getIssuedAt()
                .atZone(ZONE_ID)
                .toLocalDate()
                .format(DATE_FORMATTER);

        this.validationCode = certificate.getValidationCode();
    }

}
