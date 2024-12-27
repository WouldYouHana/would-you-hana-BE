package com.hanaro.wouldyouhana.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String sendVerificationCode(String toEmail) {
        String code = generateVerificationCode();

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject("Email Verification Code");
            helper.setText("인증 코드: " + code);

            mailSender.send(message);
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }

        return code;
    }

    private String generateVerificationCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }
}
