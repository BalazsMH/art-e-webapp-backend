package com.arte.backend.controller.favorites;

import com.arte.backend.model.favorites.FavoriteFolderModel;
import com.arte.backend.model.favorites.FavoritesModel;
import com.arte.backend.service.favorites.FavoritesService;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/favorites")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class FavoritesController {
    private FavoritesService favoritesService;

    @GetMapping("/{userName}")
    public Set<FavoritesModel> getFavoritesByUserId(@PathVariable @NotNull String userName) {
        return favoritesService.getFavoritesByUserName(userName);
    }

    @GetMapping("/byFolder/{userName}/{folderName}")
    public Set<FavoritesModel> getFavoritesByUserIdAndFolder(@PathVariable @NotNull String userName, @PathVariable @NotNull String folderName) {
        return favoritesService.getFavoritesByUserNameAndFolder(userName, folderName);
    }

    @GetMapping("/{userName}/{objectId}")
    public boolean isFavoriteByObjectId(@PathVariable @NotNull String userName, @PathVariable @NotNull String objectId) {
        return favoritesService.isFavoriteByObjectId(userName, objectId);
    }

    @PostMapping("/{userName}/{objectId}")
    public void addToFavorites (@PathVariable @NotNull String userName, @PathVariable @NotNull String objectId){
        favoritesService.addToFavorites(userName, objectId);
    }

    @PostMapping("/{userName}/{objectId}/{folderName}")
    public void addToFavorites(@PathVariable @NotNull String userName, @PathVariable @NotNull String objectId, @PathVariable(required = false) String folderName) {
        favoritesService.addToFavorites(userName, objectId, folderName);
    }

    @DeleteMapping("/{userName}/{objectId}")
    public void deleteFavoriteByObjectId(@PathVariable @NotNull String userName, @PathVariable @NotNull String objectId) {
        favoritesService.deleteFavoriteByObjectId(userName, objectId);
    }

    @PostMapping("/addFolder/{userName}/{folderName}/{colorHex}")
    public void addFavoriteFolder(@PathVariable @NotNull String userName, @PathVariable @NotNull String folderName, @PathVariable @NotNull String colorHex) {
        favoritesService.addFavoriteFolder(userName, folderName, colorHex);
    }

    @DeleteMapping("/deleteFolder/{userName}/{folderName}")
    public void deleteFavoriteFolder(@PathVariable @NotNull String userName, @PathVariable @NotNull String folderName) {
        favoritesService.deleteFavoriteFolder(userName, folderName);
    }

    @PutMapping("/renameFolder/{userName}/{oldFolderName}/{newFolderName}")
    public void renameFavoriteFolder(@PathVariable @NotNull String userName, @PathVariable @NotNull String oldFolderName, @PathVariable @NotNull String newFolderName) {
        favoritesService.renameFavoriteFolder(userName, oldFolderName, newFolderName);
    }

    @PutMapping("/changeColor/{userName}/{folderName}/{newColor}")
    public void changeFavoriteFolderColor(@PathVariable @NotNull String userName, @PathVariable @NotNull String folderName, @PathVariable @NotNull String newColor) {
        favoritesService.changeFavoriteFolderColor(userName, folderName, newColor);
    }

    @GetMapping("/getFolders/{userName}")
    public Set<FavoriteFolderModel> getFoldersByUserName(@PathVariable @NotNull String userName) {
        return favoritesService.getFoldersByUserName(userName);
    }
}
