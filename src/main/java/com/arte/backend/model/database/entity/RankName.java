package com.arte.backend.model.database.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RankName {
    ROOKIE("Rookie"),
    ARTIST("Artist");
    //TODO:Define rank names that make sense

    private final String name;

}
