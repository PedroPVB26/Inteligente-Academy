package br.edu.utfpr.inteligenteacademy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CertificateFile {

    private byte[] content;
    private String fileName;
}
