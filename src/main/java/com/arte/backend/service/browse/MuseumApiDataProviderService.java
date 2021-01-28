package com.arte.backend.service.browse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class MuseumApiDataProviderService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    private String apiURL = "https://www.rijksmuseum.nl/api/en/collection?key=Gz1ZRsyI&format=json";

    public String getArtData() {
        String response = webClientBuilder.build()
                .get()
                .uri(apiURL)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println("Api call finished");
        return response;
    }

}
