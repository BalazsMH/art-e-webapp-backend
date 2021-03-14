package com.arte.backend.controller.favorites;

import com.arte.backend.model.favorites.FavoriteFolderModel;
import com.arte.backend.security.JwtTokenServices;
import com.arte.backend.service.favorites.FavoriteFolderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favoriteFolder")
@CrossOrigin(origins = "${frontend.address}")
public class FavoriteFolderController {
    private final FavoriteFolderService favoriteFolderService;
    private final JwtTokenServices jwtTokenServices;

    public FavoriteFolderController(FavoriteFolderService favoriteFolderService, JwtTokenServices jwtTokenServices) {
        this.favoriteFolderService = favoriteFolderService;
        this.jwtTokenServices = jwtTokenServices;
    }

    @GetMapping("/getFolders")
    public List<FavoriteFolderModel> getFoldersByUserName(@RequestHeader("Authorization") String bearerToken) {
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

    @PutMapping("/changeFolder/{oldFolderName}/{newFolderName}/{newColor}")
    public void renameFavoriteFolder(@RequestHeader("Authorization") String bearerToken, @PathVariable String oldFolderName, @PathVariable String newFolderName, @PathVariable String newColor) {
        String token = jwtTokenServices.getTokenFromHeader(bearerToken);
        String email = jwtTokenServices.getEmailFromTokenInfo(token);
        favoriteFolderService.modifyFavoriteFolder(email, oldFolderName, newFolderName, newColor);
    }

    @DeleteMapping("/deleteFolder/{folderName}")
    public void deleteFavoriteFolder(@RequestHeader("Authorization") String bearerToken, @PathVariable String folderName) {
        String token = jwtTokenServices.getTokenFromHeader(bearerToken);
        String email = jwtTokenServices.getEmailFromTokenInfo(token);
        favoriteFolderService.deleteFavoriteFolder(email, folderName);
    }
}
