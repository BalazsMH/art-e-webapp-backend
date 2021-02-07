package com.arte.backend.model.database.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserStatistics {
    @Id
    @GeneratedValue
    private long id;
    private long actualXp;
    private int allAnswers;
    private int correctAnswers;
    private int winStreak;
    private int dailyStreak;
    private int dailyRemainingXp;
    @ManyToOne
    private RankData rank;
    @OneToOne
    @EqualsAndHashCode.Exclude
    private UserData user;

}
