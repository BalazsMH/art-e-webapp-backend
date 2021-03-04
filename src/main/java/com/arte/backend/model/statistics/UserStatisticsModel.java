package com.arte.backend.model.statistics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserStatisticsModel implements Serializable {
    @JsonProperty("userName")
    private String userName;
    @JsonProperty("actualXp")
    private int actualXp;
    @JsonProperty("allAnswers")
    private int allAnswers;
    @JsonProperty("correctAnswers")
    private int correctAnswers;
    @JsonProperty("dailyRemainingXp")
    private int dailyRemainingXp;
    @JsonProperty("winStreak")
    private int winStreak;
}
