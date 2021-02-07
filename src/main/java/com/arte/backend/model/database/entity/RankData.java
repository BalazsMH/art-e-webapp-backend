package com.arte.backend.model.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RankData {
    @Id
    @GeneratedValue
    private long id;
    @Enumerated
    private RankName name;
    private String badgeUrl;
    private long xpLimit;
    @OneToMany(mappedBy = "rank")
    private Set<UserStatistics> userStatisticsSet;
}
