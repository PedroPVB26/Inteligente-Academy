package br.edu.utfpr.inteligenteacademy.listener;

import br.edu.utfpr.inteligenteacademy.model.event.CourseCompletedEvent;
import br.edu.utfpr.inteligenteacademy.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CertificateListener {

    private final CertificateService certificateService;

    @EventListener
    public void handle(CourseCompletedEvent event) {
        certificateService.generateCertificate(
                event.enrollmentId()
        );
    }
}