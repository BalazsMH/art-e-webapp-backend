package com.arte.backend.controller.favorites;

import com.arte.backend.model.favorites.FavoritesModel;
import com.arte.backend.service.favorites.FavoritesService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/favorites")
@CrossOrigin(origins = "http://localhost:3000")
public class FavoritesController {
    private FavoritesService favoritesService;

    public FavoritesController(FavoritesService favoritesService) {
        this.favoritesService = favoritesService;
    }

    @GetMapping("/byFolder/{userName}/{folderName}")
    public Set<FavoritesModel> getFavoritesByUserIdAndFolder(@PathVariable String userName, @PathVariable String folderName) {
        return favoritesService.getFavoritesByUserNameAndFolder(userName, folderName);
    }

    @PostMapping("/{userName}/{objectId}/{folderName}")
    public void addToFavoriteFolder(@PathVariable String userName, @PathVariable String objectId, @PathVariable(required = false) String folderName) {
        favoritesService.addToFavorites(userName, objectId, folderName);
    }

    @GetMapping("/{userName}")
    public Set<FavoritesModel> getFavoritesByUserId(@PathVariable String userName) {
        return favoritesService.getFavoritesByUserName(userName);
    }

    @GetMapping("/{userName}/{objectId}")
    public boolean isFavoriteByObjectId(@PathVariable String userName, @PathVariable String objectId) {
        return favoritesService.isFavoriteByObjectId(userName, objectId);
    }

    @PostMapping("/{userName}/{objectId}")
    public void addToFavorites (@PathVariable String userName, @PathVariable String objectId){
        favoritesService.addToFavorites(userName, objectId);
    }

    @DeleteMapping("/{userName}/{objectId}")
    public void deleteFavoriteByObjectId(@PathVariable String userName, @PathVariable String objectId) {
        favoritesService.deleteFavoriteByObjectId(userName, objectId);
    }
}
