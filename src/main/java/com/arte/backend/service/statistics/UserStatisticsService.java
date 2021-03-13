package com.arte.backend.service.statistics;

import com.arte.backend.model.database.entity.RankData;
import com.arte.backend.model.database.entity.UserData;
import com.arte.backend.model.database.entity.UserStatistics;
import com.arte.backend.model.database.repository.UserRankRepository;
import com.arte.backend.model.statistics.UserStatisticsDataModel;
import com.arte.backend.model.database.repository.UserRepository;
import com.arte.backend.model.database.repository.UserStatisticsRepository;
import com.arte.backend.security.JwtTokenServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserStatisticsService {
    private final UserStatisticsRepository userStatisticsRepository;
    private final UserRepository userRepository;
    private final JwtTokenServices jwtTokenServices;
    private final UserRankRepository userRankRepository;
    private final long MAX_USER_XP = 110000;

    public UserStatisticsService(UserStatisticsRepository userStatisticsRepository, UserRepository userRepository, JwtTokenServices jwtTokenServices, UserRankRepository userRankRepository) {
        this.userStatisticsRepository = userStatisticsRepository;
        this.userRepository = userRepository;
        this.jwtTokenServices = jwtTokenServices;
        this.userRankRepository = userRankRepository;
    }

    public UserStatistics getUserStatistics(String token) {
        String email = jwtTokenServices.getEmailFromTokenInfo(token);
        Optional<UserData> user = userRepository.findByEmail(email);
        return user.map(UserData::getUserStatistics).orElse(null);
    }

    public void updateUserStatistics(String userData, String token) throws JsonProcessingException {
        UserStatisticsDataModel newUserStatistics = new ObjectMapper().readValue(userData, UserStatisticsDataModel.class);
        String email = jwtTokenServices.getEmailFromTokenInfo(token);
        Optional<UserData> user = userRepository.findByEmail(email);
        UserStatistics userStatistics = user.map(UserData::getUserStatistics).orElse(null);

        if (userStatistics != null) {
            long rankId = userStatistics.getRank().getId();
            long actualXp = newUserStatistics.getUserStatisticsModel().getActualXp();

            userStatistics.setActualXp(actualXp);
            userStatistics.setAllAnswers(newUserStatistics.getUserStatisticsModel().getAllAnswers());
            userStatistics.setCorrectAnswers(newUserStatistics.getUserStatisticsModel().getCorrectAnswers());
            userStatistics.setDailyRemainingXp(newUserStatistics.getUserStatisticsModel().getDailyRemainingXp());
            userStatistics.setWinStreak(newUserStatistics.getUserStatisticsModel().getWinStreak());

            RankData newUserRank = getNewUserRank(rankId, actualXp);

            if (newUserRank != null) {
                userStatistics.setRank(newUserRank);
            }

            userStatisticsRepository.save(userStatistics);
        }
    }

    public RankData getNewUserRank(long rankId, long actualXp) {
        long newRankId = rankId;
        Optional<RankData> rankDataOptional = userRankRepository.findById(rankId);
        Long xpLimit = rankDataOptional.map(RankData::getXpLimit).orElse(null);

        if (xpLimit != null && actualXp > xpLimit && actualXp < MAX_USER_XP) {
            newRankId = rankId + 1;
        }

        Optional<RankData> newRankDataOptional = userRankRepository.findById(newRankId);

        return newRankDataOptional.get();
    }

    public List<String> getAllRankNames(){
        List<RankData> ranks = userRankRepository.findBy();
        return ranks.stream().map(rankData -> rankData.getName().getName()).collect(Collectors.toList());
    }
}
