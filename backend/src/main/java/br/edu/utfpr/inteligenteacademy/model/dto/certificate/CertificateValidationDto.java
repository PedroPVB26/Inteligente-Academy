package br.edu.utfpr.inteligenteacademy.model.dto.certificate;

import lombok.Getter;

@Getter
public class CertificateValidationDto {

    private final boolean valid;
    private final String message;
    private final CertificateViewDto certificate;

    public CertificateValidationDto(boolean valid, String message, CertificateViewDto certificate) {
        this.valid = valid;
        this.message = message;
        this.certificate = certificate;
    }
}