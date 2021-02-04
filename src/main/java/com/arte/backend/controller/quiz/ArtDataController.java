package com.arte.backend.controller.quiz;

import com.arte.backend.model.quiz.ArtObjectsList;
import com.arte.backend.model.quiz.QuestionModel;
import com.arte.backend.model.quiz.QuizModel;
import com.arte.backend.service.quiz.QuizDataProviderService;
import com.arte.backend.util.quiz.PageNumberGenerator;
import com.arte.backend.util.quiz.QuizGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
public class ArtDataController {
    private String resultsPerPage = "20";

    @Autowired
    QuizDataProviderService quizDataProviderService;

    @Autowired
    PageNumberGenerator pageNumberGenerator;

    @CrossOrigin
    @PostMapping
    public String getQuiz(@RequestBody String quizType) throws JsonProcessingException {
        String pageNumber = pageNumberGenerator.generateRandomPageNumber();
        System.out.println(quizType);
        String artObject = quizDataProviderService.getDataForQuiz(pageNumber, resultsPerPage, true);
        ObjectMapper mapper = new ObjectMapper();
        ArtObjectsList artObjectsList = mapper.readValue(artObject, ArtObjectsList.class);
        QuizGenerator quizGenerator = new QuizGenerator(artObjectsList);
        QuizModel quiz = quizGenerator.generateQuiz(new QuizModel());
        String quizJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(quiz);
        return quizJson;
    }
}
