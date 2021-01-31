package com.arte.backend.service.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ArtDetailsProviderService {

    @Autowired
    private WebClient.Builder webClientBuilder;
    private final String apiURL = "https://www.rijksmuseum.nl/api/en/collection";
    private final String apiKey = "Gz1ZRsyI";
    private final String format = "json";

    public String getArtDetails(String objectNumber) {

        String response = webClientBuilder.build()
                .get()
                .uri(apiURL, uriBuilder -> uriBuilder
                        .pathSegment("{objectNumber}")
                        .queryParam("key", apiKey)
                        .queryParam("format", format)
                        .build(objectNumber)
                )
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return response;
    }
}
