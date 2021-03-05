package com.arte.backend.controller.favorites;

import com.arte.backend.model.database.entity.FavoriteFolder;
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
public class FavoritesController {
    private FavoritesService favoritesService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{userName}")
    public Set<FavoritesModel> getFavoritesByUserId(@PathVariable @NotNull String userName) {
        return favoritesService.getFavoritesByUserName(userName);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/byFolder/{userName}/{folderName}")
    public Set<FavoritesModel> getFavoritesByUserIdAndFolder(@PathVariable @NotNull String userName, @PathVariable @NotNull String folderName) {
        return favoritesService.getFavoritesByUserNameAndFolder(userName, folderName);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{userName}/{objectId}")
    public boolean isFavoriteByObjectId(@PathVariable @NotNull String userName, @PathVariable @NotNull String objectId) {
        return favoritesService.isFavoriteByObjectId(userName, objectId);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/{userName}/{objectId}")
    public void addToFavorites(@PathVariable @NotNull String userName, @PathVariable @NotNull String objectId) {
        favoritesService.addToFavorites(userName, objectId);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/{userName}/{objectId}/{folderName}")
    public void addToFavorites(@PathVariable @NotNull String userName, @PathVariable @NotNull String objectId, @PathVariable(required = false) String folderName) {
        favoritesService.addToFavorites(userName, objectId, folderName);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/{userName}/{objectId}")
    public void deleteFavoriteByObjectId(@PathVariable @NotNull String userName, @PathVariable @NotNull String objectId) {
        favoritesService.deleteFavoriteByObjectId(userName, objectId);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/addFolder/{userName}/{folderName}/{colorHex}")
    public void addFavoriteFolder(@PathVariable @NotNull String userName, @PathVariable @NotNull String folderName, @PathVariable @NotNull String colorHex) {
        favoritesService.addFavoriteFolder(userName, folderName, colorHex);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/deleteFolder/{userName}/{folderName}")
    public void deleteFavoriteFolder(@PathVariable @NotNull String userName, @PathVariable @NotNull String folderName) {
        favoritesService.deleteFavoriteFolder(userName, folderName);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/renameFolder/{userName}/{oldFolderName}/{newFolderName}")
    public void renameFavoriteFolder(@PathVariable @NotNull String userName, @PathVariable @NotNull String oldFolderName, @PathVariable @NotNull String newFolderName) {
        favoritesService.renameFavoriteFolder(userName, oldFolderName, newFolderName);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/changeColor/{userName}/{folderName}/{newColor}")
    public void changeFavoriteFolderColor(@PathVariable @NotNull String userName, @PathVariable @NotNull String folderName, @PathVariable @NotNull String newColor) {
        favoritesService.changeFavoriteFolderColor(userName, folderName, newColor);
    }

    @CrossOrigin//(origins = "http://localhost:3000")
    @GetMapping("/getFolders/{userName}")
    public Set<FavoriteFolderModel> getFoldersByUserName(@PathVariable @NotNull String userName) {
        return favoritesService.getFoldersByUserName(userName);
    }
}
