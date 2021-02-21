package com.arte.backend.controller.favorites;

import com.arte.backend.model.favorites.FavoritesModel;
import com.arte.backend.service.favorites.FavoritesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/favorites")
@AllArgsConstructor
public class FavoritesController {
    private FavoritesService favoritesService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{userName}")
    public Set<FavoritesModel> getFavoritesByUserId(@PathVariable String userName) {
        return favoritesService.getFavoritesByUserName(userName);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{userName}/{objectId}")
    public boolean isFavoriteByObjectId(@PathVariable String userName, @PathVariable String objectId) {
        return favoritesService.isFavoriteByObjectId(userName, objectId);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/{userName}/{objectId}")
    public void addToFavorites(@PathVariable String userName, @PathVariable String objectId) throws JsonProcessingException {
        favoritesService.addToFavorites(userName, objectId);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/{userName}/{objectId}")
    public void deleteFavoriteByObjectId(@PathVariable String userName, @PathVariable String objectId) {
        favoritesService.deleteFavoriteByObjectId(userName, objectId);
    }
}
