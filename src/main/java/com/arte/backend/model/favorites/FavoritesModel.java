package com.arte.backend.model.favorites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class FavoritesModel {
    private String longTitle;
    private WebImage headerImage;
    private String objectNumber;
}
