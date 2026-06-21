package br.edu.utfpr.inteligenteacademy.controller;

import br.edu.utfpr.inteligenteacademy.model.dto.certificate.CertificateViewDto;
import br.edu.utfpr.inteligenteacademy.service.CertificateViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/internal/certificates") // url utilizada por PdfGenerator
@RequiredArgsConstructor
public class CertificateInternalController { // nao deve ser esposto para nenhum usuario

    private final CertificateViewService certificateViewService;


    @GetMapping("/{enrollmentId}")
    public String renderCertificate(@PathVariable Long enrollmentId, Model model) {
        CertificateViewDto certificate = certificateViewService.getCertificateData(enrollmentId);

        model.addAttribute("certificate", certificate);

        return "certificado";
    }
}