package com.arte.backend.service.browse;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

import static com.arte.backend.config.Global.API_COLLECTION_URL;


@Service
@AllArgsConstructor
public class MuseumApiDataProviderService {
    private WebClient.Builder webClientBuilder;
    
    public String getArtData(String query, String involvedMaker, String technique,
                             String datingPeriod, String pageNumber, String resultsPerPage,
                             Boolean imgOnly, String culture) {

        String response = webClientBuilder.build()
                .get()
                .uri(API_COLLECTION_URL, uriBuilder -> uriBuilder
                        .queryParamIfPresent("p", Optional.ofNullable(pageNumber))
                        .queryParamIfPresent("ps", Optional.ofNullable(resultsPerPage))
                        .queryParamIfPresent("imgonly", Optional.ofNullable(imgOnly))
                        .queryParamIfPresent("culture", Optional.ofNullable(culture))
                        .queryParamIfPresent("q", Optional.ofNullable(query))
                        .queryParamIfPresent("involvedMaker", Optional.ofNullable(involvedMaker))
                        .queryParamIfPresent("technique", Optional.ofNullable(technique))
                        .queryParamIfPresent("datingPeriod", Optional.ofNullable(datingPeriod))
                    .build()
                )
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return response;
    }
}
