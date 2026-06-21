package br.edu.utfpr.inteligenteacademy.service;

import br.edu.utfpr.inteligenteacademy.entity.Certificate;
import br.edu.utfpr.inteligenteacademy.entity.Enrollment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class CertificateHtmlGenerator {

    private final SpringTemplateEngine templateEngine;

    private static final DateTimeFormatter brazilianDateFormatter = DateTimeFormatter
            .ofPattern("dd/MM/yyyy")
            .withLocale(Locale.of("pt", "BR"));

    public String generateHtmlCertificate(Enrollment enrollment, Certificate certificate) {

        Context context = new Context();

        context.setVariable(
                "studentName",
                enrollment.getUser().getFullName()
        );

        context.setVariable(
                "courseName",
                enrollment.getCourse().getName()
        );

        context.setVariable(
                "workload",
                enrollment.getCourse().getDurationInSeconds() / 3600
        );

        context.setVariable(
                "startDate",
                enrollment.getEnrolledAt()
                        .atZone(ZoneId.of("America/Sao_Paulo"))
                        .toLocalDate()
                        .format(brazilianDateFormatter)
        );

        context.setVariable(
                "endDate",
                enrollment.getCompletedAt()
                        .atZone(ZoneId.of("America/Sao_Paulo"))
                        .toLocalDate()
                        .format(brazilianDateFormatter)
        );

        context.setVariable(
                "validationCode",
                certificate.getValidationCode()
        );

        context.setVariable(
                "issueDate",
                certificate.getIssuedAt()
                        .atZone(ZoneId.of("America/Sao_Paulo"))
                        .toLocalDate()
                        .format(brazilianDateFormatter)
        );

        return templateEngine.process("certificado", context);
    }
}