package com.arte.backend.util.quiz;

import com.arte.backend.model.apiresponse.ArtObject;
import com.arte.backend.model.apiresponse.ArtObjectsList;
import com.arte.backend.model.quiz.QuestionModel;
import com.arte.backend.model.quiz.QuizModel;

import java.util.*;

public class QuizGenerator {
    private final ArtObjectsList apiData;
    private final String quizType;

    public QuizGenerator(ArtObjectsList apiData, String quizType) {
        this.apiData = apiData;
        this.quizType = quizType;
    }

    public QuizModel generateQuiz(QuizModel quiz) {
        QuestionModel questions = new QuestionModel();
        List<QuestionModel> questionModelList = new ArrayList<>();
        Set<String> incorrectAnswers = new HashSet<>();
        String question = quizType.equals(QuizType.DETAIL.getName()) ? QuizType.TITLE.getName() : quizType;

        int counter = 0;
        for (ArtObject artObject : apiData.getArtDataList()) {

            Map<String, String> quizAnswerTypes = Map.of(QuizType.TITLE.getName(), artObject.getTitle(), QuizType.DETAIL.getName(), artObject.getTitle(), QuizType.MAKER.getName(), artObject.getPrincipalOrFirstMaker());

            if (counter == 0) {
                questions.setCorrectAnswer(quizAnswerTypes.get(quizType));
                questions.setImgUrl(artObject.getWebImage().getUrl());
                String questionWord = (question.equals(QuizType.MAKER.getName()) ? "Who" : "What");
                questions.setQuestion(questionWord + " is the " + question + " of this picture?");
                counter++;
            }
            else {
                incorrectAnswers.add(quizAnswerTypes.get(quizType));
            }

            if (incorrectAnswers.size() == 3) {
                questions.setIncorrectAnswers(incorrectAnswers);
                questionModelList.add(questions);
                counter = 0;
                questions = new QuestionModel();
                incorrectAnswers = new HashSet<>();
            }
        }

        quiz.setResults(questionModelList);

        return quiz;
    }
}
