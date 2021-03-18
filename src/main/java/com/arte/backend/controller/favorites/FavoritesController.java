package com.arte.backend.controller.favorites;

import com.arte.backend.model.artpiece.ArtPieceModel;
import com.arte.backend.security.JwtTokenServices;
import com.arte.backend.service.favorites.FavoritesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
@CrossOrigin(origins = "${frontend.address}")
public class FavoritesController {
    private final FavoritesService favoritesService;
    private final JwtTokenServices jwtTokenServices;

    public FavoritesController(FavoritesService favoritesService, JwtTokenServices jwtTokenServices) {
        this.favoritesService = favoritesService;
        this.jwtTokenServices = jwtTokenServices;
    }

    @GetMapping("/")
    public List<ArtPieceModel> getFavoritesByEmail(@RequestHeader("Authorization") String bearerToken) {
        String token = jwtTokenServices.getTokenFromHeader(bearerToken);
        String email = jwtTokenServices.getEmailFromTokenInfo(token);
        return favoritesService.getFavoritesByEmail(email);
    }

    @GetMapping("/isFavorite/{objectName}")
    public boolean isFavoriteByObjectName(@RequestHeader("Authorization") String bearerToken, @PathVariable String objectName) {
        String token = jwtTokenServices.getTokenFromHeader(bearerToken);
        String email = jwtTokenServices.getEmailFromTokenInfo(token);
        return favoritesService.isFavoriteByObjectName(email, objectName);
    }

    @GetMapping("/getByFolder/{folderName}")
    public List<ArtPieceModel> getFavoritesByEmailAndFolder(@RequestHeader("Authorization") String bearerToken, @PathVariable String folderName) {
        String token = jwtTokenServices.getTokenFromHeader(bearerToken);
        String email = jwtTokenServices.getEmailFromTokenInfo(token);
        return favoritesService.getFavoritesByEmailAndFolder(email, folderName);
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

    @DeleteMapping("/removeFromFolder/{folderName}/{objectName}")
    public void removeFavoriteFromFolder(@RequestHeader("Authorization") String bearerToken, @PathVariable String folderName, @PathVariable String objectName) {
        String token = jwtTokenServices.getTokenFromHeader(bearerToken);
        String email = jwtTokenServices.getEmailFromTokenInfo(token);
        favoritesService.removeFavoriteFromFolder(email, folderName, objectName);
    }
}
