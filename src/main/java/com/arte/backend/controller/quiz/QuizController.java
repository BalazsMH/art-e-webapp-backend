package com.arte.backend.controller.quiz;

import com.arte.backend.service.quiz.QuizService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
@CrossOrigin(origins = "${frontend.address}")
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping
    public String getQuiz(@RequestBody String quizType) throws JsonProcessingException {
        return quizService.getQuiz(quizType);
    }
}
