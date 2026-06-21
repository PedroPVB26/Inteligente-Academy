package br.edu.utfpr.inteligenteacademy.service;

import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class FileStorageService {

    private static final String BASE_PATH = "certificates/";

    public String save(byte[] pdf, String fileName) {

        try {
            Files.createDirectories(Paths.get(BASE_PATH));

            String path = BASE_PATH + fileName + ".pdf";

            Files.write(Paths.get(path), pdf);

            return "/" + path;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar PDF", e);
        }
    }

    public byte[] load(String path) {

        try {
            String normalizedPath = path.startsWith("/")
                    ? path.substring(1)
                    : path;

            return Files.readAllBytes(
                    Paths.get(normalizedPath)
            );

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao carregar PDF: " + path,
                    e
            );
        }
    }

}