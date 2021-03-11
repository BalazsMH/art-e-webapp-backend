package com.arte.backend.model.statistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ScoreBoardModel {
    private List<ScoreBoardMemberModel> scoreBoardMemberModels;
}
