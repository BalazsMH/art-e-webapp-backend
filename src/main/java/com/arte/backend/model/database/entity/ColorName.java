package com.arte.backend.model.database.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ColorName {
    BLUE("Blue"),
    ORANGE("Orange");
    //TODO:Define color names

    private final String name;
}
