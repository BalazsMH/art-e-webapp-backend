package com.arte.backend.controller.favorites;

import com.arte.backend.model.favorites.FavoritesModel;
import com.arte.backend.service.favorites.FavoritesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@AllArgsConstructor
public class FavoritesController {
    private FavoritesService favoritesService;

    @GetMapping("/api/favorites/{userId}")
    public Set<FavoritesModel> getFavoritesByUserId(@PathVariable String userId) {
        return favoritesService.getFavoritesByUserId(userId);
    }

    @PostMapping("/api/favorites/{userId}/{objectId}")
    public void addToFavorites(@PathVariable String userId, @PathVariable String objectId) throws JsonProcessingException {
        favoritesService.addToFavorites(userId, objectId);
    }
}
