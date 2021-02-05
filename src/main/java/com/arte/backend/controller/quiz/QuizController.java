package com.arte.backend.controller.quiz;

import com.arte.backend.service.quiz.QuizService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
@AllArgsConstructor
public class QuizController {
    private QuizService quizService;

    @CrossOrigin
    @PostMapping
    public String getQuiz(@RequestBody String quizType) throws JsonProcessingException {
        return quizService.getQuiz(quizType);
    }
}
