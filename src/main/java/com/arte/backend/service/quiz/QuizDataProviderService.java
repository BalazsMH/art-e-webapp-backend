package com.arte.backend.service.quiz;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class QuizDataProviderService {
    private final WebClient.Builder webClientBuilder;

    @Value("${api.collection.url}")
    private String apiCollectionUrl;

    public QuizDataProviderService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public String getDataForQuiz(String pageNumber, String resultsPerPage, String type,
                             Boolean imgOnly) {

        String response = webClientBuilder.build()
                .get()
                .uri(apiCollectionUrl, uriBuilder -> uriBuilder
                        .queryParamIfPresent("p", Optional.ofNullable(pageNumber))
                        .queryParamIfPresent("ps", Optional.ofNullable(resultsPerPage))
                        .queryParamIfPresent("type", Optional.ofNullable(type))
                        .queryParamIfPresent("imgonly", Optional.ofNullable(imgOnly))
                        .build()
                )
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(WebClientResponseException.class, e -> Mono.empty())
                .block();

        return response;
    }
}
