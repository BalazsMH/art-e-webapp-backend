package com.arte.backend.service.favorites;

import com.arte.backend.model.apiresponse.ArtObjectsList;
import com.arte.backend.model.database.entity.Favorite;
import com.arte.backend.model.database.entity.FavoriteCollection;
import com.arte.backend.model.database.entity.UserData;
import com.arte.backend.model.favorites.FavoritesModel;
import com.arte.backend.repository.UserRepository;
import com.arte.backend.service.details.ArtDetailsProviderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@AllArgsConstructor
public class FavoritesService {
    private UserRepository userRepository;
    private ArtDetailsProviderService artDetailsProviderService;

    private void initFavorites(UserData user) {
        if (user.getFavoriteCollection() == null) {
            FavoriteCollection favoriteCollection = FavoriteCollection.builder()
                    .favoriteFolders(new HashSet<>())
                    .favorites(new HashSet<>())
                    .build();
            user.setFavoriteCollection(favoriteCollection);
        }
        userRepository.save(user);
    }

    public Set<FavoritesModel> getFavoritesByUserName(String userName) {
        Optional<UserData> optUser = userRepository.findByUserName(userName);
        if (!optUser.isPresent()) {
            return null;
        }
        UserData user = optUser.get();

        FavoriteCollection favoriteCollection = user.getFavoriteCollection();
        if (favoriteCollection == null) {
            initFavorites(user);
            favoriteCollection = user.getFavoriteCollection();
        }

        Set<Favorite> favorites = favoriteCollection.getFavorites();
        Set<FavoritesModel> favoritesModels = null;

        if (favorites.size() != 0) {
            favoritesModels = favModelFromEntity(favorites);
        }

        return favoritesModels;
    }

    private Set<FavoritesModel> favModelFromEntity(Set<Favorite> favoriteSet) {
        Set<FavoritesModel> favorites = new HashSet<>();

        Iterator<Favorite> it = favoriteSet.iterator();
        while(it.hasNext()){
            String objectNumber = it.next().getObjectNumber();
            ArtObjectsList apiData = getArtObjectsList(objectNumber);
            Optional<FavoritesModel> newFavorite = generateFavoriteFromObject(apiData, objectNumber);
            if (newFavorite.isPresent()) {
                favorites.add(newFavorite.get());
            }
        }

        return favorites.size() != 0 ? favorites : null;
    }

    private ArtObjectsList getArtObjectsList(String objectName) {
        String result = artDetailsProviderService.getArtDetails(objectName);
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

    public void addToFavorites(String userName, String objectName) {
        Optional<UserData> optUser = userRepository.findByUserName(userName);
        if (optUser.isPresent()) {
            UserData user = optUser.get();

            FavoriteCollection favoriteCollection = user.getFavoriteCollection();
            if (favoriteCollection == null) {
                initFavorites(user);
                favoriteCollection = user.getFavoriteCollection();
            }

            Favorite newFavorite = Favorite.builder()
                    .objectNumber(objectName)
                    .build();
            favoriteCollection.getFavorites().add(newFavorite);
            userRepository.save(user);
        }
    }

    public boolean isFavoriteByObjectId(String userName, String objectId) {
        long filteredSize = 0;

        Optional<UserData> optUser = userRepository.findByUserName(userName);
        if (optUser.isPresent()) {
            UserData user = optUser.get();

            FavoriteCollection favoriteCollection = user.getFavoriteCollection();
            if (favoriteCollection == null) {
                initFavorites(user);
                favoriteCollection = user.getFavoriteCollection();
            }

            filteredSize = favoriteCollection.getFavorites().stream()
                    .filter(fav -> fav.getObjectNumber().equals(objectId))
                    .count();
        }

        return filteredSize == 1;
    }

    @Transactional
    public void deleteFavoriteByObjectId(String userName, String objectId) {
        Optional<UserData> optUser = userRepository.findByUserName(userName);
        if (optUser.isPresent()) {
            UserData user = optUser.get();

            FavoriteCollection favoriteCollection = user.getFavoriteCollection();
            if (favoriteCollection == null) {
                initFavorites(user);
                favoriteCollection = user.getFavoriteCollection();
            }

            favoriteCollection.getFavorites().removeIf(artwork -> artwork.getObjectNumber().equals(objectId));
        }
    }
}
