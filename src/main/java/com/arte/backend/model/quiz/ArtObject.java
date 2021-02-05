package com.arte.backend.model.quiz;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArtObject implements Serializable {

    @JsonProperty("title")
    private String title;
    @JsonProperty("principalOrFirstMaker")
    private String principalOrFirstMaker;
    @JsonProperty("longTitle")
    private String longTitle;
    @JsonProperty("webImage")
    private ArtObjectWebImage webImage;
}
