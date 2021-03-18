package com.arte.backend.controller.user;

import com.arte.backend.service.statistics.ScoreBoardService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "${frontend.address}")
public class ScoreBoardController {

    private final ScoreBoardService scoreBoardService;

    public ScoreBoardController(ScoreBoardService scoreBoardService) {
        this.scoreBoardService = scoreBoardService;
    }

    @GetMapping("/scoreboard")
    public HashMap<String, List> getStatistics() {
        return scoreBoardService.getScoreBoard();
    }
}
