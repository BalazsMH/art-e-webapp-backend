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
@Table(name = "rank_data")
public class RankData {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    @Enumerated
    private RankName name;
    private String badgeUrl;
    private long xpLimit;
}
