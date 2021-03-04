package com.arte.backend.model.statistics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserStatisticsDataModel implements Serializable {
    @JsonProperty("stats")
    private UserStatisticsModel userStatisticsModel;
}
