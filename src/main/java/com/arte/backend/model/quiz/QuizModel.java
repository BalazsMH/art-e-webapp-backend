package com.arte.backend.model.quiz;

import lombok.Data;
import java.io.Serializable;
import java.util.List;


@Data
public class QuizModel implements Serializable {
    private List<QuestionModel> results;
}
