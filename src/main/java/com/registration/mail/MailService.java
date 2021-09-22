package com.registration.mail;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class MailService implements MailSender {
    private final JavaMailSender javaMailSender;
    private final static Logger LOGGER = LoggerFactory.getLogger(MailService.class);

    @Override
    @Async
    public void send(String to, String mail) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");

            mimeMessageHelper.setText(mail, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("Account Confirmation");
            mimeMessageHelper.setFrom("system@abl.com");
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("Error in mail sending" + e);
            throw new IllegalStateException("Failed to send email");
        }

    }
}
