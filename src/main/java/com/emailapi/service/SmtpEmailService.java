package com.emailapi.service;

import com.emailapi.model.EmailRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class SmtpEmailService implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Override
    public void sendEmail(EmailRequest emailRequest) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(senderEmail);
            helper.setTo(emailRequest.getTo());

            // Handle Reply-To
            if (emailRequest.getReplyTo() != null && !emailRequest.getReplyTo().isEmpty()) {
                helper.setReplyTo(emailRequest.getReplyTo());
            }

            helper.setSubject(emailRequest.getSubject());
            helper.setText(emailRequest.getBody(), emailRequest.isHtml());

            // Note: Attachments are skipped for the basic JSON API implementation
            // to keep the request simple.

            mailSender.send(message);
            System.out.println("Email Sent Successfully to " + emailRequest.getTo());

        } catch (MessagingException e) {
            throw new RuntimeException("Error while sending email: " + e.getMessage(), e);
        }
    }
}
