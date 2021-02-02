package com.arte.backend.model.favorites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class FavoritesModel {
    private String title;
    private String imageUrl;
    private String objectId;
}
