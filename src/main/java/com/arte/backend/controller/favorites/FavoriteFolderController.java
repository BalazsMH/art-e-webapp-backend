package com.arte.backend.controller.favorites;

import com.arte.backend.model.favorites.FavoriteFolderModel;
import com.arte.backend.service.favorites.FavoriteFolderService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/favorites")
@CrossOrigin(origins = "http://localhost:3000")
public class FavoriteFolderController {
    private FavoriteFolderService favoriteFolderService;

    public FavoriteFolderController(FavoriteFolderService favoriteFolderService) {
        this.favoriteFolderService = favoriteFolderService;
    }

    @PostMapping("/addFolder/{userName}/{folderName}/{colorHex}")
    public void addFavoriteFolder(@PathVariable String userName, @PathVariable String folderName, @PathVariable String colorHex) {
        favoriteFolderService.addFavoriteFolder(userName, folderName, colorHex);
    }

    @DeleteMapping("/deleteFolder/{userName}/{folderName}")
    public void deleteFavoriteFolder(@PathVariable String userName, @PathVariable String folderName) {
        favoriteFolderService.deleteFavoriteFolder(userName, folderName);
    }

    @PutMapping("/renameFolder/{userName}/{oldFolderName}/{newFolderName}")
    public void renameFavoriteFolder(@PathVariable String userName, @PathVariable String oldFolderName, @PathVariable String newFolderName) {
        favoriteFolderService.renameFavoriteFolder(userName, oldFolderName, newFolderName);
    }

    @PutMapping("/changeColor/{userName}/{folderName}/{newColor}")
    public void changeFavoriteFolderColor(@PathVariable String userName, @PathVariable String folderName, @PathVariable String newColor) {
        favoriteFolderService.changeFavoriteFolderColor(userName, folderName, newColor);
    }

    @GetMapping("/getFolders/{userName}")
    public Set<FavoriteFolderModel> getFoldersByUserName(@PathVariable String userName) {
        return favoriteFolderService.getFoldersByUserName(userName);
    }
}
