package com.arte.backend.model.database.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Favorite {
    @Id
    @GeneratedValue
    private long id;
    private String objectNumber;
    @ManyToMany(mappedBy = "favorites", cascade = CascadeType.PERSIST)
    @Singular
    @EqualsAndHashCode.Exclude
    private Set<FavoriteFolder> favoriteFolders;
}
