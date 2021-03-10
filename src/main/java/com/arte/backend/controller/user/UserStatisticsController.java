package com.arte.backend.controller.user;

import com.arte.backend.model.database.entity.UserStatistics;
import com.arte.backend.security.JwtTokenServices;
import com.arte.backend.service.statistics.UserStatisticsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserStatisticsController {
    private final UserStatisticsService userStatisticsService;
    private final JwtTokenServices jwtTokenServices;

    public UserStatisticsController(UserStatisticsService userStatisticsService, JwtTokenServices jwtTokenServices) {
        this.userStatisticsService = userStatisticsService;
        this.jwtTokenServices = jwtTokenServices;
    }

    @PostMapping("/statistics")
    public UserStatistics getStatistics(HttpServletRequest request) {
        String token = jwtTokenServices.getTokenFromRequest(request);
        return token != null ? userStatisticsService.getUserStatistics(token) : null;
    }

    @PostMapping("/update-statistics")
    public void updateUserStatistics(HttpServletRequest request, @RequestBody String userData) throws JsonProcessingException {
        String token = jwtTokenServices.getTokenFromRequest(request);
        if (token != null) {
            userStatisticsService.updateUserStatistics(userData, token);
        }
    }
}


