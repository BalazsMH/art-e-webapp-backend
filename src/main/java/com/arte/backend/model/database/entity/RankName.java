package com.arte.backend.model.database.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RankName {
    SECONDLIEUTENANT("Art Second Lieutenant"),
    FIRSTLIEUTENANT("Art First Lieutenant"),
    CAPTAIN("Art Captain"),
    MAJOR("Art Major"),
    LIEUTENANTCOLONEL("Art Lieutenant Colonel"),
    COLONEL("Art Staff Colonel"),
    BRIGADIERGENERAL("Art Brigadier General"),
    MAJORGENERAL("Art Major General"),
    LIEUTENANTGENERAL("Art Lieutenant General"),
    GENERAL("Art General");

    private final String name;

}
