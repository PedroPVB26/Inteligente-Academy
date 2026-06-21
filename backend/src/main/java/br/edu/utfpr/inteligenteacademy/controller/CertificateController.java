package br.edu.utfpr.inteligenteacademy.controller;

import br.edu.utfpr.inteligenteacademy.entity.User;
import br.edu.utfpr.inteligenteacademy.model.CertificateFile;
import br.edu.utfpr.inteligenteacademy.model.dto.certificate.CertificateValidationDto;
import br.edu.utfpr.inteligenteacademy.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/certificates")
@RequiredArgsConstructor
public class CertificateController {
    private final CertificateService certificateService;

    @GetMapping("/{enrollmentId}/download")
    @ResponseBody
    public ResponseEntity<byte[]> downloadCertificate(
            @PathVariable Long enrollmentId,
            @AuthenticationPrincipal User user
    ) {

        CertificateFile certificateFile = certificateService.downloadCertificate(
                enrollmentId,
                user.getId()
        );

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                certificateFile.getFileName() +
                                "\""
                )
                .body(certificateFile.getContent());
    }

    @GetMapping("/validate/{validationCode}")
    @ResponseBody
    public ResponseEntity<CertificateValidationDto> validateCertificate(
            @PathVariable String validationCode
    ) {
        return ResponseEntity.ok(certificateService.validateByValidationCode(validationCode));
    }
}
