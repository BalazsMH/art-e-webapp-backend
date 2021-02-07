package com.arte.backend.model.database.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteFolder {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @ManyToOne
    private UserData user;
    @ManyToOne
    private ColorPicker color;
}
