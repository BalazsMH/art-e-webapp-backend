package com.arte.backend.model.database.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "favorite")
public class Favorite {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    private String objectNumber;
}
