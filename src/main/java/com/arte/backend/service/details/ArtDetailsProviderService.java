package com.arte.backend.service.details;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class ArtDetailsProviderService {
    private final WebClient.Builder webClientBuilder;

    @Value("${api.single.url}")
    private String apiSingleUrl;

    @Value("${api.key}")
    private String apiKey;

    @Value("${api.response.format}")
    private String apiResponseFormat;

    public ArtDetailsProviderService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public String getArtDetails(String objectNumber) {

        String response = webClientBuilder.build()
                .get()
                .uri(apiSingleUrl, uriBuilder -> uriBuilder
                        .pathSegment("{objectNumber}")
                        .queryParam("key", apiKey)
                        .queryParam("format", apiResponseFormat)
                        .build(objectNumber)
                )
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(WebClientResponseException.class, e -> Mono.empty())
                .block();
        return response;
    }
}
