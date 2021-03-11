package com.arte.backend.model.statistics;

import java.util.Comparator;

public class ScoreBoardSorter implements Comparator<ScoreBoardMemberModel>{

    @Override
    public int compare(ScoreBoardMemberModel o1, ScoreBoardMemberModel o2) {
        Integer o1Xp = Math.toIntExact(o1.getUserXp());
        Integer o2Xp = Math.toIntExact(o2.getUserXp());;
        return o1Xp.compareTo(o2Xp) * -1;
    }
}
