package com.arte.backend.service.statistics;

import com.arte.backend.model.database.entity.UserData;
import com.arte.backend.model.database.entity.UserStatistics;
import com.arte.backend.model.statistics.UserStatisticsDataModel;
import com.arte.backend.repository.UserRepository;
import com.arte.backend.repository.UserStatisticsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserStatisticsService {
    UserStatisticsRepository userStatisticsRepository;
    UserRepository userRepository;

    public UserStatisticsService(UserStatisticsRepository userStatisticsRepository, UserRepository userRepository) {
        this.userStatisticsRepository = userStatisticsRepository;
        this.userRepository = userRepository;
    }

    public UserStatistics getUserStatistics(String userName) {
        Optional<UserData> user = userRepository.findByUserName(userName);
        return user.map(UserData::getUserStatistics).orElse(null);
    }

    public void updateUserStatistics(String userData) throws JsonProcessingException {
        UserStatisticsDataModel newUserStatistics = new ObjectMapper().readValue(userData, UserStatisticsDataModel.class);
        Optional<UserData> user = userRepository.findByUserName(newUserStatistics.getUserStatisticsModel().getUserName());

        UserStatistics userStatistics = user.map(UserData::getUserStatistics).orElse(null);;

        if (userStatistics != null) {
            userStatistics.setActualXp(newUserStatistics.getUserStatisticsModel().getActualXp());
            userStatistics.setAllAnswers(newUserStatistics.getUserStatisticsModel().getAllAnswers());
            userStatistics.setCorrectAnswers(newUserStatistics.getUserStatisticsModel().getCorrectAnswers());
            userStatistics.setDailyRemainingXp(newUserStatistics.getUserStatisticsModel().getDailyRemainingXp());
            userStatistics.setWinStreak(newUserStatistics.getUserStatisticsModel().getWinStreak());
            userStatisticsRepository.save(userStatistics);
        }
    }
}
