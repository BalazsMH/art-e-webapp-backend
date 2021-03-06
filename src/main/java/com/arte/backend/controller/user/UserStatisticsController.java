package com.arte.backend.controller.user;

import com.arte.backend.model.database.entity.UserStatistics;
import com.arte.backend.service.statistics.UserStatisticsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserStatisticsController {

    UserStatisticsService userStatisticsService;

    @PostMapping("/{userName}/statistics")
    public UserStatistics getStatistics(@PathVariable String userName) {
        return userStatisticsService.getUserStatistics(userName);
    }

    @PostMapping("/update-statistics")
    public void updateUserStatistics(@RequestBody String userData) throws JsonProcessingException {
        userStatisticsService.updateUserStatistics(userData);
    }
}


