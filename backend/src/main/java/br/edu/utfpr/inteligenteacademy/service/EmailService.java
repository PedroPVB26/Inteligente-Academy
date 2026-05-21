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
    public void enviarEmail(String destinatario, String assunto, String html) {
    	try {
    		MimeMessage message = mailSender.createMimeMessage();
    		
    		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
    		
    		helper.setFrom(fromEmail);
    		helper.setTo(destinatario);
    		helper.setSubject(assunto);
    		
    		helper.setText(html, true);
    		
    		mailSender.send(message);
    		
    	}catch(Exception e) {
    		 e.printStackTrace();
    		throw new RuntimeException("Error while sending email");
    	}
    }
}
