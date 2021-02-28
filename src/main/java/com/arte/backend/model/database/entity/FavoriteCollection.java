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
@Table(name = "favorite_collection")
public class FavoriteCollection {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    @OneToMany
    @JoinColumn(name = "favorite_collection_id", referencedColumnName = "id")
    private Set<FavoriteFolder> favoriteFolders;
    @OneToMany
    @JoinColumn(name = "favorite_collection_id", referencedColumnName = "id")
    private Set<Favorite> favorites;
}
