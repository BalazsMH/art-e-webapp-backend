package com.arte.backend.controller.favorites;

import com.arte.backend.model.favorites.FavoritesModel;
import com.arte.backend.service.favorites.FavoritesService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/favorites")
@CrossOrigin(origins = "http://localhost:3000")
public class FavoritesController {
    private final FavoritesService favoritesService;

    public FavoritesController(FavoritesService favoritesService) {
        this.favoritesService = favoritesService;
    }

    @GetMapping("/isFavorite/{userName}/{objectName}")
    public boolean isFavoriteByObjectName(@PathVariable String userName, @PathVariable String objectName) {
        return favoritesService.isFavoriteByObjectName(userName, objectName);
    }

    @GetMapping("/{userName}")
    public Set<FavoritesModel> getFavoritesByUserName(@PathVariable String userName) {
        return favoritesService.getFavoritesByUserName(userName);
    }

    @GetMapping("/getByFolder/{userName}/{folderName}")
    public Set<FavoritesModel> getFavoritesByUserNameAndFolder(@PathVariable String userName, @PathVariable String folderName) {
        return favoritesService.getFavoritesByUserNameAndFolder(userName, folderName);
    }

    @PostMapping("/{userName}/{objectName}")
    public void addToFavorites (@PathVariable String userName, @PathVariable String objectName){
        favoritesService.addToFavorites(userName, objectName);
    }

    @PostMapping("/addFavorite/{userName}/{objectName}/{folderName}")
    public void addToFavoriteFolder(@PathVariable String userName, @PathVariable String objectName, @PathVariable(required = false) String folderName) {
        favoritesService.addToFavorites(userName, objectName, folderName);
    }

    @DeleteMapping("/{userName}/{objectName}")
    public void deleteFavoriteByObjectName(@PathVariable String userName, @PathVariable String objectName) {
        favoritesService.deleteFavoriteByObjectName(userName, objectName);
    }
}
