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
@Table(name = "favorite_folder")
public class FavoriteFolder {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    private String name;
    private String colorHex;
    @ManyToMany
    @JoinColumn(name = "favorite_id", referencedColumnName = "id")
    private Set<Favorite> favorites;
}
