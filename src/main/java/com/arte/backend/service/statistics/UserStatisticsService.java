package com.arte.backend.service.statistics;

import com.arte.backend.model.database.entity.UserData;
import com.arte.backend.model.database.entity.UserStatistics;
import com.arte.backend.repository.UserRepository;
import com.arte.backend.repository.UserStatisticsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserStatisticsService {
    UserStatisticsRepository userStatisticsRepository;
    UserRepository userRepository;

    public UserStatistics userStatistics(String userName) {
        Optional<UserData> user = userRepository.findByUserName(userName);
        return user.map(UserData::getUserStatistics).orElse(null);
    }
}
