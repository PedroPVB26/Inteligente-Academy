package br.edu.utfpr.inteligenteacademy.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AppProperties {

    @Value("${app.base-url}")
    private String baseUrl;

    @Value("${app.internal-token}")
    private String internalToken;
}