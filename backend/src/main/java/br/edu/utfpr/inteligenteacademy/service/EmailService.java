package br.edu.utfpr.inteligenteacademy.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    
    private final String fromEmail;
    
    public EmailService(JavaMailSender mailSender, @Value("${spring.mail.username}") String fromEmail) {
    	this.mailSender = mailSender;
    	this.fromEmail = fromEmail;
    }
    
    @Async
    public void sendVerificatioEmail(String destination, String subject, String html) { // MUDAR AQUI, POIS O EMAIL QUE DEVE GERAR O HTML COM BASE NOS DADOS RECEBIDOS
    	try {
    		MimeMessage message = mailSender.createMimeMessage();
    		
    		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
    		
    		helper.setFrom(fromEmail);
    		helper.setTo(destination);
    		helper.setSubject(subject);
    		
    		helper.setText(html, true);
    		
    		mailSender.send(message);
    		
    	}catch(Exception e) {
    		 e.printStackTrace();
    		throw new RuntimeException("Error while sending verification email");
    	}
    }
    
    @Async
    public void sendPasswordResetEmail(String destination, String rawToken) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

            helper.setTo(destination);
            helper.setSubject("Redefinição de senha (TESTE)");

            String html = """
                <html>
                    <body>
                        <p><strong>EMAIL DE TESTE - REDEFINIÇÃO DE SENHA</strong></p>

                        <p>Seu token de redefinição é:</p>

                        <h2>%s</h2>

                    </body>
                </html>
                """.formatted(rawToken);

            helper.setText(html, true);

            mailSender.send(mimeMessage);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while sending reset password email");
        }
    }
}
