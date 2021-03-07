package com.arte.backend.controller.favorites;

import com.arte.backend.model.favorites.FavoriteFolderModel;
import com.arte.backend.security.JwtTokenServices;
import com.arte.backend.service.favorites.FavoriteFolderService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/favoriteFolder")
@CrossOrigin(origins = "http://localhost:3000")
public class FavoriteFolderController {
    private final FavoriteFolderService favoriteFolderService;
    private final JwtTokenServices jwtTokenServices;

    public FavoriteFolderController(FavoriteFolderService favoriteFolderService, JwtTokenServices jwtTokenServices) {
        this.favoriteFolderService = favoriteFolderService;
        this.jwtTokenServices = jwtTokenServices;
    }

    @GetMapping("/getFolders")
    public Set<FavoriteFolderModel> getFoldersByUserName(@RequestHeader("Authorization") String bearerToken) {
        String token = jwtTokenServices.getTokenFromHeader(bearerToken);
        String email = jwtTokenServices.getEmailFromTokenInfo(token);
        return favoriteFolderService.getFoldersByUserName(email);
    }

    @PostMapping("/addFolder/{folderName}/{colorHex}")
    public void addFavoriteFolder(@RequestHeader("Authorization") String bearerToken, @PathVariable String folderName, @PathVariable String colorHex) {
        String token = jwtTokenServices.getTokenFromHeader(bearerToken);
        String email = jwtTokenServices.getEmailFromTokenInfo(token);
        favoriteFolderService.addFavoriteFolder(email, folderName, colorHex);
    }

    @PutMapping("/renameFolder/{userName}/{oldFolderName}/{newFolderName}")
    public void renameFavoriteFolder(@PathVariable String userName, @PathVariable String oldFolderName, @PathVariable String newFolderName) {
        favoriteFolderService.renameFavoriteFolder(userName, oldFolderName, newFolderName);
    }

    @PutMapping("/changeFolderColor/{userName}/{folderName}/{newColor}")
    public void changeFavoriteFolderColor(@PathVariable String userName, @PathVariable String folderName, @PathVariable String newColor) {
        favoriteFolderService.changeFavoriteFolderColor(userName, folderName, newColor);
    }

    @DeleteMapping("/deleteFolder/{userName}/{folderName}")
    public void deleteFavoriteFolder(@PathVariable String userName, @PathVariable String folderName) {
        favoriteFolderService.deleteFavoriteFolder(userName, folderName);
    }
}
