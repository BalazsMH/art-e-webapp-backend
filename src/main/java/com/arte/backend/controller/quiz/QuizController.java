package com.arte.backend.controller.quiz;

import com.arte.backend.model.quiz.QuizModel;
import com.arte.backend.service.browse.MuseumApiDataProviderService;
import com.arte.backend.service.quiz.QuizDataProviderService;
import com.arte.backend.service.quiz.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class QuizController {
    @Autowired
    QuizService quizService;
    @Autowired
    QuizDataProviderService quizDataProviderService;


    @GetMapping("/quiz")
    public String renderBrowse() {
        return quizService.browse();
    }

    @CrossOrigin
    @GetMapping("/test/quiz")
    public String returnQuizData() {

        return quizDataProviderService.getDataForQuiz("Rembrandt+van+Rijn", "2", "4", true);
    }
}
