package com.arte.backend.service.browse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;


@Service
public class MuseumApiDataProviderService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    private final String apiURL = "https://www.rijksmuseum.nl/api/en/collection?key=Gz1ZRsyI&format=json";

    public String getArtData(String query, String involvedMaker, String technique,
                             String datingPeriod, String pageNumber, String resultsPerPage,
                             Boolean imgOnly, String culture) {

        String response = webClientBuilder.build()
                .get()
                .uri(apiURL, uriBuilder -> uriBuilder
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
