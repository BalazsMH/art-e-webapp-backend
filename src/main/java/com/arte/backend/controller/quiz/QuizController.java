package com.arte.backend.controller.quiz;

import com.arte.backend.model.quiz.ArtObjectsList;
import com.arte.backend.model.quiz.QuizModel;
import com.arte.backend.model.quiz.QuizType;
import com.arte.backend.service.quiz.QuizDataProviderService;
import com.arte.backend.util.quiz.PageNumberGenerator;
import com.arte.backend.util.quiz.QuizGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
@AllArgsConstructor
public class QuizController {
    private final String resultsPerPage = "20";
    private final String type = "painting";

    QuizDataProviderService quizDataProviderService;
    PageNumberGenerator pageNumberGenerator;

    @CrossOrigin
    @PostMapping
    public String getQuiz(@RequestBody String quizType) throws JsonProcessingException {
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
