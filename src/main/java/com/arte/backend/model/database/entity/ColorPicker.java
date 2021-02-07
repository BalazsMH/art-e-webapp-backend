package com.arte.backend.model.database.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColorPicker {
    @Id
    @GeneratedValue
    private long id;
    @Enumerated
    private ColorName name;
    private String colorIndex;
    @OneToMany(mappedBy = "color", cascade = CascadeType.PERSIST)
    @Singular
    @EqualsAndHashCode.Exclude
    private Set<FavoriteFolder> favoriteFolders;
}
