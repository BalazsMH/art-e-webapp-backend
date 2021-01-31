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

    public QuizModel generateQuiz(QuizModel quiz) {
        QuestionModel questions = new QuestionModel();
        List<QuestionModel> questionModelList = new ArrayList<>();
        List<String> incorrectAnswers = new ArrayList<>();
        int counter = 0;
        int questionCounter = 1;
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

            if (questionCounter % 4 == 0) {
                questions.setIncorrectAnswers(incorrectAnswers);
                questionModelList.add(questions);
                counter = 0;
                questions = new QuestionModel();
                incorrectAnswers = new ArrayList<>();
            }

            questionCounter++;
        }

        quiz.setResults(questionModelList);

        return quiz;
    }
}
