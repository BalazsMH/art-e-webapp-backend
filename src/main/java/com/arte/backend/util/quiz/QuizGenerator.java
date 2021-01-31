package com.arte.backend.util.quiz;

import com.arte.backend.model.quiz.ArtObject;
import com.arte.backend.model.quiz.ArtObjectsList;
import com.arte.backend.model.quiz.QuestionModel;
import com.arte.backend.model.quiz.QuizModel;


import java.util.ArrayList;
import java.util.List;

public class QuizGenerator {
    private ArtObjectsList apiData;

    public QuizGenerator(ArtObjectsList apiData) {
        this.apiData = apiData;
    }

    public QuizModel generateQuiz(QuestionModel questions, QuizModel quiz) {
        List<String> incorrectAnswers = new ArrayList<>();
        int counter = 0;
        for (ArtObject artObject : apiData.getArtData()) {
            if (counter == 0) {
                questions.setCorrectAnswer(artObject.getTitle());
                questions.setImgUrl(artObject.getWebImage().getUrl());
                questions.setQuestion("What is the title of this picture");
                counter++;
            }
            else {
                incorrectAnswers.add(artObject.getTitle());
            }
        }
        questions.setIncorrectAnswers(incorrectAnswers);
        List<QuestionModel> questionModelList = new ArrayList<>();
        questionModelList.add(questions);
        quiz.setResults(questionModelList);

        return quiz;
    }
}
