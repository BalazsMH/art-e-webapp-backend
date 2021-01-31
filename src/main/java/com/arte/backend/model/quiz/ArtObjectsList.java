package com.arte.backend.model.quiz;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArtObjectsList {
    @JsonProperty("artObjects")
    private List<ArtObject> artData;
}
