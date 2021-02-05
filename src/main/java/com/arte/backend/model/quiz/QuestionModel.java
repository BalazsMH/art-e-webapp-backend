package com.arte.backend.model.quiz;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Set;

@Data
public class QuestionModel {
    private String question;
    @JsonProperty("correct_answer")
    private String correctAnswer;
    @JsonProperty("incorrect_answers")
    private Set<String> incorrectAnswers;
    @JsonProperty("url")
    private String imgUrl;
}
