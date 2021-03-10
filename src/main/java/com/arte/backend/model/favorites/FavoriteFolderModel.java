package com.arte.backend.model.favorites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class FavoriteFolderModel {
    private Long id;
    private String name;
    private String colorHex;
}
