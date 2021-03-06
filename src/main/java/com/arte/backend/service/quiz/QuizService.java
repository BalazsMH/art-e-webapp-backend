package com.arte.backend.service.quiz;

import com.arte.backend.model.apiresponse.ArtObjectsList;
import com.arte.backend.model.quiz.QuizModel;
import com.arte.backend.model.quiz.QuizType;
import com.arte.backend.util.quiz.PageNumberGenerator;
import com.arte.backend.util.quiz.QuizGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class QuizService {
    private final String resultsPerPage = "20";
    private final String type = "painting";

    private QuizDataProviderService quizDataProviderService;
    private PageNumberGenerator pageNumberGenerator;

    public QuizService(QuizDataProviderService quizDataProviderService, PageNumberGenerator pageNumberGenerator) {
        this.quizDataProviderService = quizDataProviderService;
        this.pageNumberGenerator = pageNumberGenerator;
    }

    public String getQuiz(String quizType) throws JsonProcessingException {
        String pageNumber = pageNumberGenerator.generateRandomPageNumber();
        String artObject = quizDataProviderService.getDataForQuiz(pageNumber, resultsPerPage, type, true);
        ObjectMapper mapper = new ObjectMapper();

        ArtObjectsList artObjectsList = mapper.readValue(artObject, ArtObjectsList.class);
        QuizType type = mapper.readValue(quizType, QuizType.class);

        QuizGenerator quizGenerator = new QuizGenerator(artObjectsList, type.getQuizType());
        QuizModel quiz = quizGenerator.generateQuiz(new QuizModel());
        String quizJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(quiz);

        return quizJson;
    }
}
