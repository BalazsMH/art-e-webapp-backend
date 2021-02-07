package com.arte.backend.model.database.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

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
    @OneToOne(mappedBy = "userStatistics", cascade = CascadeType.PERSIST)
    @EqualsAndHashCode.Exclude
    private UserData user;

}
