package com.arte.backend.model.database.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_statistics")
public class UserStatistics {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    private long actualXp;
    private int allAnswers;
    private int correctAnswers;
    private int winStreak;
    private int dailyStreak;
    private int dailyRemainingXp;

    @ManyToOne
    @JoinColumn(name = "rank_id", referencedColumnName = "id")
    private RankData rank;
}
