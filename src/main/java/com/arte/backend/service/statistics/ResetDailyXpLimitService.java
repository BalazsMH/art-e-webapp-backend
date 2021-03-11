package com.arte.backend.service.statistics;

import com.arte.backend.model.database.entity.UserData;
import com.arte.backend.model.database.entity.UserStatistics;
import com.arte.backend.model.database.repository.UserRepository;
import com.arte.backend.model.database.repository.UserStatisticsRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResetDailyXpLimitService {
    private final UserStatisticsRepository userStatisticsRepository;
    private final UserRepository userRepository;

    public ResetDailyXpLimitService(UserStatisticsRepository userStatisticsRepository, UserRepository userRepository) {
        this.userStatisticsRepository = userStatisticsRepository;
        this.userRepository = userRepository;
    }

    @Scheduled(cron = "${cron.expression}")
    public void resetDailyXpLimit() {
        List<UserData> users = userRepository.findAll();
        for (UserData user : users) {
            if (user != null) {
                UserStatistics userStatistics = user.getUserStatistics();
                userStatistics.setDailyRemainingXp(1500);
                userStatisticsRepository.save(userStatistics);
            }
        }
    }
}
