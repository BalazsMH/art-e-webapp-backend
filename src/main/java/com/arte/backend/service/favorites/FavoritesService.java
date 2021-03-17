package com.arte.backend.service.favorites;

import com.arte.backend.model.apiresponse.ArtObjectsList;
import com.arte.backend.model.database.entity.*;
import com.arte.backend.model.favorites.FavoritesModel;
import com.arte.backend.model.database.repository.UserRepository;
import com.arte.backend.service.details.ArtDetailsProviderService;
import com.arte.backend.util.helper.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class FavoritesService {
    private final UserRepository userRepository;
    private final ArtDetailsProviderService artDetailsProviderService;
    private final FavoriteHelper favoriteHelper;

    public FavoritesService(UserRepository userRepository, ArtDetailsProviderService artDetailsProviderService, FavoriteHelper favoriteHelper) {
        this.userRepository = userRepository;
        this.artDetailsProviderService = artDetailsProviderService;
        this.favoriteHelper = favoriteHelper;
    }

    public List<FavoritesModel> getFavoritesByUserName(String email) {
        FavoriteCollection favoriteCollection = favoriteHelper.getFavoriteCollection(email);
        if (favoriteCollection != null) {
            Set<Favorite> favorites = favoriteCollection.getFavorites();

            return Converter.favoritesModelSetToSortedListByTitle(favModelFromEntity(favorites));
        }
        return null;
    }

    public List<FavoritesModel> getFavoritesByUserNameAndFolder(String email, String folderName) {
        FavoriteCollection favoriteCollection = favoriteHelper.getFavoriteCollection(email);
        if (favoriteCollection != null) {
            Optional<FavoriteFolder> favoriteFolder = favoriteCollection.getFavoriteFolders()
                    .stream()
                    .filter(folder -> folder.getName().equals(folderName))
                    .findFirst();
            Set<Favorite> favorites = favoriteFolder.map(FavoriteFolder::getFavorites).orElse(null);

            return Converter.favoritesModelSetToSortedListByTitle(favModelFromEntity(favorites));
        }
        return null;
    }

    public void addToFavorites(String email, String objectName) {
        FavoriteCollection favoriteCollection = favoriteHelper.getFavoriteCollection(email);
        if (favoriteCollection != null) {
            Favorite newFavorite = Favorite.builder()
                    .objectNumber(objectName)
                    .build();
            favoriteCollection.getFavorites().add(newFavorite);

            userRepository.save(userRepository.findByEmail(email).get());
        }
    }

    public void addToFavorites(String email, String objectName, String folderName) {
        FavoriteCollection favoriteCollection = favoriteHelper.getFavoriteCollection(email);
        if (favoriteCollection != null) {
            Optional<Favorite> favorite = favoriteCollection.getFavorites().stream().filter(f -> f.getObjectNumber().equals(objectName)).findFirst();
            if (favorite.isPresent()) {
                Optional<FavoriteFolder> favoriteFolder = favoriteCollection.getFavoriteFolders().stream()
                        .filter(f -> f.getName().equals(folderName))
                        .findFirst();
                favoriteFolder.ifPresent(folder -> folder.getFavorites().add(favorite.get()));
            }
            userRepository.save(userRepository.findByEmail(email).get());
        }
    }

    public boolean isFavoriteByObjectName(String email, String objectName) {
        long filteredSize = 0;

        FavoriteCollection favoriteCollection = favoriteHelper.getFavoriteCollection(email);
        if (favoriteCollection != null) {
            filteredSize = favoriteCollection.getFavorites().stream()
                    .filter(fav -> fav.getObjectNumber().equals(objectName))
                    .count();
        }

        return filteredSize == 1;
    }

    @Transactional
    public void deleteFavoriteByObjectName(String email, String objectName) {
        FavoriteCollection favoriteCollection = favoriteHelper.getFavoriteCollection(email);
        if (favoriteCollection != null) {
            favoriteCollection.getFavoriteFolders()
                    .forEach(fol -> fol.getFavorites()
                            .removeIf(f -> f.getObjectNumber().equals(objectName)));

            favoriteCollection.getFavorites().removeIf(artwork -> artwork.getObjectNumber().equals(objectName));
        }
    }

    @Transactional
    public void removeFavoriteFromFolder(String email, String folderName, String objectName) {
        FavoriteCollection favoriteCollection = favoriteHelper.getFavoriteCollection(email);
        if (favoriteCollection != null) {
            favoriteCollection.getFavoriteFolders()
                    .stream()
                    .filter(fol -> fol.getName().equals(folderName))
                    .forEach(fol -> fol.getFavorites()
                            .removeIf(f -> f.getObjectNumber().equals(objectName)));
        }
    }

    private Set<FavoritesModel> favModelFromEntity(Set<Favorite> favoriteSet) {
        if (favoriteSet == null) { return null; }
        Set<FavoritesModel> favorites = new HashSet<>();

        for (Favorite favorite : favoriteSet) {
            String objectNumber = favorite.getObjectNumber();
            ArtObjectsList apiData = getArtObjectsList(objectNumber);
            Optional<FavoritesModel> newFavorite = generateFavoriteFromObject(apiData, objectNumber);
            newFavorite.ifPresent(favorites::add);
        }

        return favorites.size() != 0 ? favorites : null;
    }

    private ArtObjectsList getArtObjectsList(String objectName) {
        String result = artDetailsProviderService.getArtDetails(objectName);
        if (result == null) return null;
        ArtObjectsList completeData = null;
        try {
            completeData = new ObjectMapper().readValue(result, ArtObjectsList.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return completeData;
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
}
