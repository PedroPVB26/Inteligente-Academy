package br.edu.utfpr.inteligenteacademy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Habilita o suporte a execução assíncrona no Spring Boot.
 *
 * A anotação @EnableAsync ativa o processamento de métodos anotados com @Async,
 * permitindo que tarefas mais pesadas (como envio de emails) sejam executadas
 * em threads separadas, sem bloquear a thread principal da aplicação.
 *
 * Isso melhora a performance e evita que requisições HTTP fiquem aguardando
 * operações demoradas, como comunicação com servidores SMTP.
 *
 * Esta configuração é necessária porque o Spring não habilita execução assíncrona
 * automaticamente — ela precisa ser ativada explicitamente no contexto da aplicação.
 */
@Configuration
@EnableAsync
public class AsyncConfig {
}