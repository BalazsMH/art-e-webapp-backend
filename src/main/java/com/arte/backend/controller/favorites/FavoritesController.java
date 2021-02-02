package com.arte.backend.controller.favorites;

import com.arte.backend.model.favorites.FavoritesModel;
import com.arte.backend.service.favorites.FavoritesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
public class FavoritesController {
    private FavoritesService favoritesService;

    @CrossOrigin
    @GetMapping("/api/favorites/{userId}")
    public Set<FavoritesModel> getFavoritesByUserId(@PathVariable String userId) {
        return favoritesService.getFavoritesByUserId(userId);
    }

    @CrossOrigin
    @PostMapping("/api/favorites/{userId}/{objectId}")
    public void addToFavorites(@PathVariable String userId, @PathVariable String objectId) throws JsonProcessingException {
        favoritesService.addToFavorites(userId, objectId);
    }

    @CrossOrigin
    @DeleteMapping("/api/favorites/{userId}/{objectId}")
    public void deleteFavoriteByObjectId(@PathVariable String userId, @PathVariable String objectId) {
        favoritesService.deleteFavoriteByObjectId(userId, objectId);
    }
}
