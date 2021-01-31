package com.arte.backend.controller.quiz;

import com.arte.backend.service.browse.MuseumApiDataProviderService;
import com.arte.backend.service.quiz.QuizDataProviderService;
import com.arte.backend.service.quiz.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/api/quiz")
    public String returnQuizData() {

        return quizDataProviderService.getDataForQuiz("Rembrandt+van+Rijn", "2", "4", true);
    }
}
