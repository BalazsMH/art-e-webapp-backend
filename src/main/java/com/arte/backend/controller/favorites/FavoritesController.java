package com.arte.backend.controller.favorites;

import com.arte.backend.model.favorites.FavoritesModel;
import com.arte.backend.security.JwtTokenServices;
import com.arte.backend.service.favorites.FavoritesService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/favorites")
@CrossOrigin(origins = "http://localhost:3000")
public class FavoritesController {
    private final FavoritesService favoritesService;
    private final JwtTokenServices jwtTokenServices;

    public FavoritesController(FavoritesService favoritesService, JwtTokenServices jwtTokenServices) {
        this.favoritesService = favoritesService;
        this.jwtTokenServices = jwtTokenServices;
    }

    @GetMapping("/")
    public Set<FavoritesModel> getFavoritesByUserName(@RequestHeader("Authorization") String bearerToken) {
        String token = jwtTokenServices.getTokenFromHeader(bearerToken);
        String email = jwtTokenServices.getEmailFromTokenInfo(token);
        return favoritesService.getFavoritesByUserName(email);
    }

    @GetMapping("/isFavorite/{objectName}")
    public boolean isFavoriteByObjectName(@RequestHeader("Authorization") String bearerToken, @PathVariable String objectName) {
        String token = jwtTokenServices.getTokenFromHeader(bearerToken);
        String email = jwtTokenServices.getEmailFromTokenInfo(token);
        return favoritesService.isFavoriteByObjectName(email, objectName);
    }

    @GetMapping("/getByFolder/{folderName}")
    public Set<FavoritesModel> getFavoritesByUserNameAndFolder(@RequestHeader("Authorization") String bearerToken, @PathVariable String folderName) {
        String token = jwtTokenServices.getTokenFromHeader(bearerToken);
        String email = jwtTokenServices.getEmailFromTokenInfo(token);
        return favoritesService.getFavoritesByUserNameAndFolder(email, folderName);
    }

    @PostMapping("/{objectName}")
    public void addToFavorites (@RequestHeader("Authorization") String bearerToken, @PathVariable String objectName){
        String token = jwtTokenServices.getTokenFromHeader(bearerToken);
        String email = jwtTokenServices.getEmailFromTokenInfo(token);
        favoritesService.addToFavorites(email, objectName);
    }

    @PostMapping("/addFavorite/{objectName}/{folderName}")
    public void addToFavoriteFolder(@RequestHeader("Authorization") String bearerToken, @PathVariable String objectName, @PathVariable(required = false) String folderName) {
        String token = jwtTokenServices.getTokenFromHeader(bearerToken);
        String email = jwtTokenServices.getEmailFromTokenInfo(token);
        favoritesService.addToFavorites(email, objectName, folderName);
    }

    @DeleteMapping("/{objectName}")
    public void deleteFavoriteByObjectName(@RequestHeader("Authorization") String bearerToken, @PathVariable String objectName) {
        String token = jwtTokenServices.getTokenFromHeader(bearerToken);
        String email = jwtTokenServices.getEmailFromTokenInfo(token);
        favoritesService.deleteFavoriteByObjectName(email, objectName);
    }
}
