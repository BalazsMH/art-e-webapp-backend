package com.arte.backend.util.quiz;

import com.arte.backend.model.quiz.ArtObject;
import com.arte.backend.model.quiz.ArtObjectsList;
import com.arte.backend.model.quiz.QuestionModel;
import com.arte.backend.model.quiz.QuizModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuizGenerator {
    private ArtObjectsList apiData;
    private String quizType;

    public QuizGenerator(ArtObjectsList apiData, String quizType) {
        this.apiData = apiData;
        this.quizType = quizType;
    }

    public QuizModel generateQuiz(QuizModel quiz) {
        QuestionModel questions = new QuestionModel();
        List<QuestionModel> questionModelList = new ArrayList<>();
        List<String> incorrectAnswers = new ArrayList<>();
        String question = quizType.equals("detail") ? "title" : quizType;

        int counter = 0;
        int questionCounter = 1;
        for (ArtObject artObject : apiData.getArtData()) {

            Map<String, String> quizAnswerTypes = Map.of("title",artObject.getTitle(), "detail", artObject.getTitle(), "maker", artObject.getPrincipalOrFirstMaker());

            if (counter == 0) {
                questions.setCorrectAnswer(quizAnswerTypes.get(quizType));
                questions.setImgUrl(artObject.getWebImage().getUrl());
                questions.setQuestion("What is the " + question + " of this picture?");
                counter++;
            }
            else {
                incorrectAnswers.add(quizAnswerTypes.get(quizType));
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
