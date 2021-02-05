package com.arte.backend.service.details;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import static com.arte.backend.config.Global.*;

@Service
@AllArgsConstructor
public class ArtDetailsProviderService {
    private WebClient.Builder webClientBuilder;

    public String getArtDetails(String objectNumber) {

        String response = webClientBuilder.build()
                .get()
                .uri(API_SINGLE_ARTWORK_URL, uriBuilder -> uriBuilder
                        .pathSegment("{objectNumber}")
                        .queryParam("key", API_KEY)
                        .queryParam("format", API_RESPONSE_FORMAT)
                        .build(objectNumber)
                )
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return response;
    }
}
