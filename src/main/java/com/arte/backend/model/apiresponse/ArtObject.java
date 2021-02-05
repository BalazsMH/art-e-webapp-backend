package com.arte.backend.model.apiresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArtObject {

    @JsonProperty("objectNumber")
    private String objectNumber;

    @JsonProperty("title")
    private String title;

    @JsonProperty("principalOrFirstMaker")
    private String principalOrFirstMaker;

    @JsonProperty("longTitle")
    private String longTitle;

    @JsonProperty("webImage")
    private WebImage webImage;
}