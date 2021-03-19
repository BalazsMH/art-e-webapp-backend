package com.arte.backend.service.statistics;

import com.arte.backend.model.database.entity.UserData;
import com.arte.backend.model.database.entity.UserStatistics;
import com.arte.backend.model.database.repository.UserRepository;
import com.arte.backend.model.statistics.ScoreBoardMemberModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ScoreBoardServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void getScoreBoard_threeUsers_giveCorrectOrderBasedOnXp() {
        // init users
        UserStatistics stat1 = UserStatistics.builder()
                .actualXp(1000).build();
        UserStatistics stat2 = UserStatistics.builder()
                .actualXp(300).build();
        UserStatistics stat3 = UserStatistics.builder()
                .actualXp(500).build();
        UserData user1 = UserData.builder().userName("Test 1").userStatistics(stat1).build();
        UserData user2 = UserData.builder().userName("Test 2").userStatistics(stat2).build();
        UserData user3 = UserData.builder().userName("Test 3").userStatistics(stat3).build();
        userRepository.saveAndFlush(user1);
        userRepository.saveAndFlush(user2);
        userRepository.saveAndFlush(user3);

        // set expected result
        List<ScoreBoardMemberModel> scoreBoardMemberModels = new ArrayList<>(){{
            add(ScoreBoardMemberModel.builder()
                    .userName("Test 1")
                    .userXp(1000)
                    .build());
            add(ScoreBoardMemberModel.builder()
                    .userName("Test 3")
                    .userXp(500)
                    .build());
            add(ScoreBoardMemberModel.builder()
                    .userName("Test 2")
                    .userXp(300)
                    .build());
        }};
        HashMap<String, List> expectedScoreBoard = new HashMap<>() {{
            put("data", scoreBoardMemberModels);
        }};

        ScoreBoardService scoreBoardService = new ScoreBoardService(userRepository);
        HashMap<String, List> scoreBoard = scoreBoardService.getScoreBoard();

        assertEquals(expectedScoreBoard, scoreBoard);
    }
}