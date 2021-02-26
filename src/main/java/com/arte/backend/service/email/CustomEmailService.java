package com.arte.backend.service.email;

import com.arte.backend.config.TemplateEngineConfiguration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class CustomEmailService {

    JavaMailSender javaMailSender;
    TemplateEngineConfiguration templateEngineConfiguration;


    public void sendConfirmationEmail(String username, String userEmail) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(userEmail);

        msg.setSubject("Welcome to Art-E!");
        msg.setText("Welcome, " + username + "!\n Your registration has been successful");

        javaMailSender.send(msg);
    }

    public void sendHtmlConfirmationEmail(String username, String userEmail) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("username", username);
        variables.put("useremail", userEmail);
        variables.put("logo", "logo");

        String output = templateEngineConfiguration
                            .templateEngineProvider()
                            .process("registration_mail", new Context(Locale.getDefault(), variables));

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.addInline("logo", new ClassPathResource("static/images/arte.png"), "image/png");

            message.setRecipients(MimeMessage.RecipientType.TO, userEmail);
            message.setSubject("Welcome to Art-E!");
            message.setContent(output, "text/html");

            javaMailSender.send(message);
            log.info("Successfully sent email to: " + userEmail);
        } catch (MessagingException e) {
            log.error("Failed to send confirmation email to: " + userEmail);
        }
    }

}
