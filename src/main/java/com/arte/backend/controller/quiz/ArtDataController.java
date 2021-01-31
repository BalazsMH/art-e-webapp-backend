package com.arte.backend.controller.quiz;

import com.arte.backend.model.quiz.ArtObjectsList;
import com.arte.backend.model.quiz.QuestionModel;
import com.arte.backend.model.quiz.QuizModel;
import com.arte.backend.service.quiz.QuizDataProviderService;
import com.arte.backend.util.quiz.QuizGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
public class ArtDataController {

    @Autowired
    QuizDataProviderService quizDataProviderService;

    @CrossOrigin
    @PostMapping
    public String getQuiz() throws JsonProcessingException {
        String artObject = quizDataProviderService.getDataForQuiz("Rembrandt+van+Rijn", "2", "20", true);
        ObjectMapper mapper = new ObjectMapper();
        ArtObjectsList artObjectsList = mapper.readValue(artObject, ArtObjectsList.class);
        QuizGenerator quizGenerator = new QuizGenerator(artObjectsList);
        QuizModel quiz = quizGenerator.generateQuiz(new QuizModel());
        String quizJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(quiz);
        return quizJson;
    }
}
