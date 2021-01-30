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
    public String returnQuizData(@RequestParam(required = false, name = "q") String query,
                                @RequestParam(required = false, name = "involvedMaker") String involvedMaker,
                                @RequestParam(required = false, name = "technique") String technique,
                                @RequestParam(required = false, name = "datingPeriod") String datingPeriod,
                                @RequestParam(required = false, name = "p") String pageNumber,
                                @RequestParam(required = false, name = "ps") String resultsPerPage,
                                @RequestParam(required = false, name = "imgonly") Boolean imgOnly,
                                @RequestParam(required = false, name = "culture") String culture) {

        return quizDataProviderService.getDataForQuiz(query, involvedMaker, technique, datingPeriod,
                pageNumber, resultsPerPage, imgOnly, culture);
    }
}
