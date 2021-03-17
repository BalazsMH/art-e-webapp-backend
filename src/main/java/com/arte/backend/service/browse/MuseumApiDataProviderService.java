package com.arte.backend.service.browse;

import com.arte.backend.model.apiresponse.ArtObject;
import com.arte.backend.model.apiresponse.ArtObjectsList;
import com.arte.backend.model.artpiece.ArtPieceModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MuseumApiDataProviderService {
    private final WebClient.Builder webClientBuilder;

    @Value("${api.collection.url}")
    private String apiCollectionUrl;

    public MuseumApiDataProviderService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public List<ArtPieceModel> getArtData(String query, String involvedMaker, String technique,
                                          String datingPeriod, String pageNumber, String resultsPerPage,
                                          Boolean imgOnly, String culture) {

        String response = webClientBuilder.build()
                .get()
                .uri(apiCollectionUrl, uriBuilder -> uriBuilder
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
                .onErrorResume(WebClientResponseException.class, e -> Mono.empty())
                .block();

        if (response == null) return null;

        ArtObjectsList artObjectsList = getArtObjectsList(response);

        List<ArtPieceModel> artPieceModels = artObjectsList.getArtDataList()
                .stream()
                .map(this::generateArtPieceModelFromObject)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        return artPieceModels;
    }

    private ArtObjectsList getArtObjectsList(String response) {
        if (response == null) return null;
        ArtObjectsList completeData = null;
        try {
            completeData = new ObjectMapper().readValue(response, ArtObjectsList.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return completeData;
    }

    private Optional<ArtPieceModel> generateArtPieceModelFromObject(ArtObject artObject) {
        if (artObject != null) {
            ArtPieceModel newFavorite = ArtPieceModel.builder()
                    .longTitle(artObject.getLongTitle())
                    .objectNumber(artObject.getObjectNumber())
                    .webImage(artObject.getWebImage())
                    .build();
            return Optional.of(newFavorite);
        }
        return Optional.empty();
    }
}
