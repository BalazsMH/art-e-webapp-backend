package com.arte.backend.service.favorites;

import com.arte.backend.model.apiresponse.ArtObjectsList;
import com.arte.backend.model.favorites.FavoritesModel;
import com.arte.backend.service.details.ArtDetailsProviderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class FavoritesService {
    private ArtDetailsProviderService artDetailsProviderService;

    private Map<String, Set<FavoritesModel>> favorites;

    public Set<FavoritesModel> getFavoritesByUserId(String userId) {
        initializeFavoritesSet(userId);
        return favorites.get(userId);
    }

    public void addToFavorites(String userId, String objectId) throws JsonProcessingException {
        String result = artDetailsProviderService.getArtDetails(objectId);
        ArtObjectsList completeData = new ObjectMapper().readValue(result, ArtObjectsList.class);
        Optional<FavoritesModel> newFavorite = generateFavoriteFromObject(completeData, objectId);
        if (newFavorite.isPresent()) {
            if (favorites.containsKey(userId)){
                favorites.get(userId).add(newFavorite.get());
            }
            else {
                favorites.put(userId, new HashSet<>() {{ add(newFavorite.get()); }});
            }
        }
    }

    private Optional<FavoritesModel> generateFavoriteFromObject(ArtObjectsList arts, String objectId) {
        if (arts != null) {
            FavoritesModel newFavorite = FavoritesModel.builder()
                                                        .longTitle(arts.getArtData().getLongTitle())
                                                        .objectNumber(objectId)
                                                        .headerImage(arts.getArtData().getWebImage())
                                                        .build();
            return Optional.of(newFavorite);
        }
        return Optional.empty();
    }

    public void deleteFavoriteByObjectId(String userId, String objectId) {
        if (favorites.containsKey(userId)) {
            favorites.get(userId).removeIf(artwork -> artwork.getObjectNumber().equals(objectId));
        }
    }

    public boolean isFavoriteByObjectId(String userId, String objectId) {
        long filteredSize = 0;

        if (favorites.containsKey(userId)) {
            filteredSize = favorites.get(userId).stream()
                    .filter(fav -> fav.getObjectNumber().equals(objectId))
                    .count();
        }

        return filteredSize == 1;
    }

    private void initializeFavoritesSet(String userId) {
        if (!favorites.containsKey(userId)) {
            favorites.put(userId, new HashSet<>());
        }
    }
}
