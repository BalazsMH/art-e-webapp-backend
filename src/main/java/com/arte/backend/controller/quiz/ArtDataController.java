package com.arte.backend.controller.quiz;

import com.arte.backend.model.quiz.ArtObjectsList;
import com.arte.backend.model.quiz.ArtObject;
import com.arte.backend.model.quiz.QuestionModel;
import com.arte.backend.model.quiz.QuizModel;
import com.arte.backend.util.quiz.QuizGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
public class ArtDataController {

    @GetMapping
    public String data() {
        return "artObject";
    }

    @PostMapping
    public String getArtObjects(@RequestBody String artObject) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ArtObjectsList artObjectsList = mapper.readValue(artObject, ArtObjectsList.class);
        QuizGenerator quizGenerator = new QuizGenerator(artObjectsList);
        QuizModel quiz = quizGenerator.generateQuiz(new QuestionModel(), new QuizModel());
        String quizJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(quiz);
        return quizJson;
    }
}
