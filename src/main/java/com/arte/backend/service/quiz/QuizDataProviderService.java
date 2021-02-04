package com.arte.backend.service.quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
public class QuizDataProviderService {
    @Autowired
    private WebClient.Builder webClientBuilder;

    private final String apiURL = "https://www.rijksmuseum.nl/api/en/collection?key=Gz1ZRsyI&format=json";

    public String getDataForQuiz(String pageNumber, String resultsPerPage,
                             Boolean imgOnly) {

        String response = webClientBuilder.build()
                .get()
                .uri(apiURL, uriBuilder -> uriBuilder
                        .queryParamIfPresent("p", Optional.ofNullable(pageNumber))
                        .queryParamIfPresent("ps", Optional.ofNullable(resultsPerPage))
                        .queryParamIfPresent("imgonly", Optional.ofNullable(imgOnly))
                        .build()
                )
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return response;
    }
}
