package com.arte.backend.service.email;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomEmailService {

    JavaMailSender javaMailSender;

    public void sendConfirmationEmail(String username, String userEmail) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(userEmail);

        msg.setSubject("Welcome to Art-E!");
        msg.setText("Welcome, " + username + "!\n Your registration has been successful");

        javaMailSender.send(msg);
    }
}
