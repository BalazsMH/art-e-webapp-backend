package com.arte.backend.controller.user;

import com.arte.backend.model.database.entity.UserStatistics;
import com.arte.backend.service.statistics.UserStatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserStatisticsController {

    UserStatisticsService userStatisticsService;

    @CrossOrigin
    @GetMapping("/{userName}/statistics")
    public UserStatistics getStatistics(@PathVariable String userName) {
        return userStatisticsService.userStatistics(userName);
    }
}


