package com.arte.backend.service.quiz;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
@AllArgsConstructor
public class QuizDataProviderService {
    private WebClient.Builder webClientBuilder;

    @Value("${api.collection.url}")
    private String apiCollectionUrl;

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
                .block();

        return response;
    }
}
