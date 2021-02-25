package com.arte.backend.service.statistics;

import com.arte.backend.model.database.entity.UserData;
import com.arte.backend.model.database.entity.UserStatistics;
import com.arte.backend.repository.UserRepository;
import com.arte.backend.repository.UserStatisticsRepository;
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

    public UserStatistics userStatistics(String userName) {
        Optional<UserData> user = userRepository.findByUserName(userName);
        if (user.isPresent()) {
            Long userId = user.get().getId();
            System.out.println(user.get().toString());
        }
        return null;
    }
}
