package com.arte.backend;

import com.arte.backend.util.quiz.QuizGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ArtEBackendApplication {
    @Bean
    public WebClient.Builder getWebClientBuilder() {
        return WebClient.builder();
    }
    public static void main(String[] args) {
        SpringApplication.run(ArtEBackendApplication.class, args);
    }

}
