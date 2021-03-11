package com.arte.backend.service.statistics;

import com.arte.backend.model.database.entity.UserData;
import com.arte.backend.model.database.repository.UserRepository;
import com.arte.backend.model.statistics.ScoreBoardMemberModel;
import com.arte.backend.model.statistics.ScoreBoardModel;
import com.arte.backend.model.statistics.ScoreBoardSorter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class ScoreBoardService {
    private final UserRepository userRepository;

    public ScoreBoardService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public HashMap<String, List> getScoreBoard() {
        List<UserData> users = userRepository.findAll();
        List<ScoreBoardMemberModel> scoreBoardMemberModelList = new ArrayList<>();
        for (UserData user : users) {
            if (user != null) {
                ScoreBoardMemberModel scoreBoardMemberModel = ScoreBoardMemberModel.builder()
                        .userName(user.getUserName())
                        .userXp(user.getUserStatistics().getActualXp())
                        .build();

                scoreBoardMemberModelList.add(scoreBoardMemberModel);
            }
        }

        ScoreBoardModel scoreBoardModel = ScoreBoardModel.builder()
                .scoreBoardMemberModels(scoreBoardMemberModelList)
                .build();

        HashMap<String, List> scoreBoard = new HashMap<>();
        List<ScoreBoardMemberModel> scoreBoardMemberModels = scoreBoardModel.getScoreBoardMemberModels();

        scoreBoardMemberModels.sort(new ScoreBoardSorter());

        scoreBoard.put("data", scoreBoardMemberModels);
        return scoreBoard;
    }
}
