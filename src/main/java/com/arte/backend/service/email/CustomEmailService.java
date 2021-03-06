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

    public void sendHtmlConfirmationEmail(String username, String userEmail) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("username", username);
        variables.put("useremail", userEmail);
        variables.put("logo", "logo.png");

        String output = templateEngineConfiguration
                            .templateEngineProvider()
                            .process("registration_mail", new Context(Locale.getDefault(), variables));

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setSubject("Welcome to Art-E!");
            helper.setTo(userEmail);
            helper.addInline("logo.png", new ClassPathResource("static/images/arte.png"), "image/png");
            helper.setText(output, true);

            javaMailSender.send(message);
            log.info("Successfully sent registration email to: " + userEmail);
        } catch (MessagingException e) {
            log.error("Failed to send confirmation email to: " + userEmail);
        }
    }

}
