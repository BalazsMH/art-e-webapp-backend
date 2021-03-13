package com.arte.backend.service.email;

import com.arte.backend.config.TemplateEngineConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
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
@Slf4j
public class CustomEmailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngineConfiguration templateEngineConfiguration;

    public CustomEmailService(JavaMailSender javaMailSender, TemplateEngineConfiguration templateEngineConfiguration) {
        this.javaMailSender = javaMailSender;
        this.templateEngineConfiguration = templateEngineConfiguration;
    }

    public void sendHtmlConfirmationEmail(String username, String userEmail) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("username", username);
        variables.put("useremail", userEmail);

        String output = templateEngineConfiguration
                            .templateEngineProvider()
                            .process("registration_mail", new Context(Locale.getDefault(), variables));

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setSubject("Welcome to Art-E!");
            helper.setTo(userEmail);
            helper.setText(output, true);
            helper.addInline("logo", new ClassPathResource("static/images/arte.png"), "image/png");


            javaMailSender.send(message);
            log.info("Successfully sent registration email to: " + userEmail);
        } catch (MessagingException e) {
            log.error("Failed to send confirmation email to: " + userEmail);
        }
    }

}
