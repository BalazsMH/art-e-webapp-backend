package com.arte.backend.model.favorites;

import com.arte.backend.model.apiresponse.ArtObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArtObjectsList {
    @JsonProperty("artObject")
    private ArtObject artData;
}