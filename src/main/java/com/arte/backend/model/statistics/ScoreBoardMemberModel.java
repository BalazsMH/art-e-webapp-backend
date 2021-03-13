package com.arte.backend.model.statistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ScoreBoardMemberModel {
    String userName;
    long userXp;
}
