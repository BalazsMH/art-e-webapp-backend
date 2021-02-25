package com.arte.backend.service.favorites;

import com.arte.backend.model.apiresponse.ArtObjectsList;
import com.arte.backend.model.database.entity.Favorite;
import com.arte.backend.model.database.entity.FavoriteFolder;
import com.arte.backend.model.database.entity.UserData;
import com.arte.backend.model.database.repository.FavoriteFolderRepository;
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
    private static final String DEFAULT_FAVORITE_FOLDER_NAME = "default";
    private FavoriteFolderRepository favoriteFolderRepository;
    private UserRepository userRepository;
    private ArtDetailsProviderService artDetailsProviderService;


    private void initFolder(UserData user) {
        List<FavoriteFolder> favFolders = favoriteFolderRepository.findAllByUser(user);
        if (favFolders.size() == 0) {
            FavoriteFolder defaultFolder = FavoriteFolder.builder()
                    .name(DEFAULT_FAVORITE_FOLDER_NAME)
                    .user(user)
                    .build();
            favoriteFolderRepository.save(defaultFolder);
        }
    }

    // TODO: extend to custom folders
    public Set<FavoritesModel> getFavoritesByUserName(String userName) {
        Optional<UserData> user = userRepository.findByUserName(userName);
        if (!user.isPresent()) {
            return null;
        }

        initFolder(user.get());
        FavoriteFolder defaultFolder = favoriteFolderRepository.findByUserAndName(user.get(), DEFAULT_FAVORITE_FOLDER_NAME);

        return favModelFromEntity(defaultFolder.getFavorites());
    }

    private Set<FavoritesModel> favModelFromEntity(Set<Favorite> favoriteSet) {
        Set<FavoritesModel> favorites = new HashSet<>();
        favoriteSet.stream().forEach(fav -> {
            ArtObjectsList apiData = getArtObjectsList(fav.getObjectNumber());
            Optional<FavoritesModel> newFavorite = generateFavoriteFromObject(apiData, fav.getObjectNumber());
            if (newFavorite.isPresent()) {
                favorites.add(newFavorite.get());
            }
        });
        return null;
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
        Optional<UserData> user = userRepository.findByUserName(userName);
        if (user.isPresent()) {
            FavoriteFolder defaultFolder = favoriteFolderRepository.findByUserAndName(user.get(), DEFAULT_FAVORITE_FOLDER_NAME);
            Favorite newFavorite = Favorite.builder()
                    .objectNumber(objectName)
                    .favoriteFolder(defaultFolder)
                    .build();
            defaultFolder.getFavorites().add(newFavorite);
            favoriteFolderRepository.save(defaultFolder);
        }
    }




    private Map<String, Set<FavoritesModel>> favorites;

//    public Set<FavoritesModel> getFavoritesByUserName(String userName) {
//        initializeFavoritesSet(userName);
//        return favorites.get(userName);
//    }

//    public void addToFavorites(String userName, String objectId) throws JsonProcessingException {
//        String result = artDetailsProviderService.getArtDetails(objectId);
//        ArtObjectsList completeData = new ObjectMapper().readValue(result, ArtObjectsList.class);
//        Optional<FavoritesModel> newFavorite = generateFavoriteFromObject(completeData, objectId);
//        if (newFavorite.isPresent()) {
//            if (favorites.containsKey(userName)){
//                favorites.get(userName).add(newFavorite.get());
//            }
//            else {
//                favorites.put(userName, new HashSet<>() {{ add(newFavorite.get()); }});
//            }
//        }
//    }



    public void deleteFavoriteByObjectId(String userName, String objectId) {
        if (favorites.containsKey(userName)) {
            favorites.get(userName).removeIf(artwork -> artwork.getObjectNumber().equals(objectId));
        }
    }

    public boolean isFavoriteByObjectId(String userName, String objectId) {
        long filteredSize = 0;

        if (favorites.containsKey(userName)) {
            filteredSize = favorites.get(userName).stream()
                    .filter(fav -> fav.getObjectNumber().equals(objectId))
                    .count();
        }

        return filteredSize == 1;
    }

//    private void initializeFavoritesSet(String userName) {
//        if (!favorites.containsKey(userName)) {
//            favorites.put(userName, new HashSet<>());
//        }
//    }
}
