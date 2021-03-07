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

    @PutMapping("/renameFolder/{oldFolderName}/{newFolderName}")
    public void renameFavoriteFolder(@RequestHeader("Authorization") String bearerToken, @PathVariable String oldFolderName, @PathVariable String newFolderName) {
        String token = jwtTokenServices.getTokenFromHeader(bearerToken);
        String email = jwtTokenServices.getEmailFromTokenInfo(token);
        favoriteFolderService.renameFavoriteFolder(email, oldFolderName, newFolderName);
    }

    @PutMapping("/changeFolderColor/{folderName}/{newColor}")
    public void changeFavoriteFolderColor(@RequestHeader("Authorization") String bearerToken, @PathVariable String folderName, @PathVariable String newColor) {
        String token = jwtTokenServices.getTokenFromHeader(bearerToken);
        String email = jwtTokenServices.getEmailFromTokenInfo(token);
        favoriteFolderService.changeFavoriteFolderColor(email, folderName, newColor);
    }

    @DeleteMapping("/deleteFolder/{folderName}")
    public void deleteFavoriteFolder(@RequestHeader("Authorization") String bearerToken, @PathVariable String folderName) {
        String token = jwtTokenServices.getTokenFromHeader(bearerToken);
        String email = jwtTokenServices.getEmailFromTokenInfo(token);
        favoriteFolderService.deleteFavoriteFolder(email, folderName);
    }
}
