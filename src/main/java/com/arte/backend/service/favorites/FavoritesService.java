package com.arte.backend.service.favorites;

import com.arte.backend.model.apiresponse.ArtObjectsList;
import com.arte.backend.model.database.entity.*;
import com.arte.backend.model.favorites.FavoriteFolderModel;
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
        return getFavoritesByUserNameAndFolder(userName, null);
    }

    public Set<FavoritesModel> getFavoritesByUserNameAndFolder(String userName, String folderName) {
        Set<FavoritesModel> favoritesModels = null;

        FavoriteCollection favoriteCollection = getFavoriteCollection(userName);
        if (favoriteCollection != null) {
            Set<Favorite> favorites = null;
            if (folderName == null) {
                favorites = favoriteCollection.getFavorites();
            }
            else {
                for (FavoriteFolder folder : favoriteCollection.getFavoriteFolders()) {
                    if (folder.getName().equals(folderName)) {
                        favorites = folder.getFavorites();
                    }
                }
            }

            if (favorites.size() != 0) {
                favoritesModels = favModelFromEntity(favorites);
            }
        }

        return favoritesModels;
    }

    private Set<FavoritesModel> favModelFromEntity(Set<Favorite> favoriteSet) {
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

    public void addToFavorites(String userName, String objectId) {
        addToFavorites(userName,objectId, null);
    }

    public void addToFavorites(String userName, String objectName, String folderName) {
        FavoriteCollection favoriteCollection = getFavoriteCollection(userName);
        if (favoriteCollection != null) {
            if (folderName == null) {
                Favorite newFavorite = Favorite.builder()
                        .objectNumber(objectName)
                        .build();
                favoriteCollection.getFavorites().add(newFavorite);
            }
            else {
                Optional<Favorite> favorite = favoriteCollection.getFavorites().stream().filter(f -> f.getObjectNumber().equals(objectName)).findFirst();
                if (favorite.isPresent()) {
                    Optional<FavoriteFolder> favoriteFolder = favoriteCollection.getFavoriteFolders().stream().filter(f -> f.getName().equals(folderName)).findFirst();
                    if (favoriteFolder.isPresent()) {
                        favoriteFolder.get().getFavorites().add(favorite.get());
                    }
                }
            }
            userRepository.save(userRepository.findByUserName(userName).get());
        }
    }

    public boolean isFavoriteByObjectId(String userName, String objectId) {
        long filteredSize = 0;

        FavoriteCollection favoriteCollection = getFavoriteCollection(userName);
        if (favoriteCollection != null) {
            filteredSize = favoriteCollection.getFavorites().stream()
                    .filter(fav -> fav.getObjectNumber().equals(objectId))
                    .count();
        }

        return filteredSize == 1;
    }

    @Transactional
    public void deleteFavoriteByObjectId(String userName, String objectId) {
        FavoriteCollection favoriteCollection = getFavoriteCollection(userName);
        if (favoriteCollection != null) {
            favoriteCollection.getFavoriteFolders()
                    .forEach(fol -> fol.getFavorites()
                            .removeIf(f -> f.getObjectNumber().equals(objectId)));

            favoriteCollection.getFavorites().removeIf(artwork -> artwork.getObjectNumber().equals(objectId));
        }
    }

    public void addFavoriteFolder(String userName, String folderName, String colorHex) {
        FavoriteCollection favoriteCollection = getFavoriteCollection(userName);
        if (favoriteCollection != null) {
            FavoriteFolder newFolder = FavoriteFolder.builder()
                    .name(folderName)
                    .colorHex(colorHex)
                    .favorites(new HashSet<>())
                    .build();

            favoriteCollection.getFavoriteFolders().add(newFolder);
            userRepository.save(userRepository.findByUserName(userName).get());
        }
    }

    @Transactional
    public void deleteFavoriteFolder(String userName, String folderName) {
        FavoriteCollection favoriteCollection = getFavoriteCollection(userName);
        if (favoriteCollection != null) {
            favoriteCollection.getFavoriteFolders().removeIf(folder -> folder.getName().equals(folderName));
        }
    }

    @Transactional
    public void renameFavoriteFolder(String userName, String oldFolderName, String newFolderName) {
        FavoriteCollection favoriteCollection = getFavoriteCollection(userName);
        if (favoriteCollection != null) {
            for (FavoriteFolder folder : favoriteCollection.getFavoriteFolders()) {
                if (folder.getName().equals(oldFolderName)) {
                    folder.setName(newFolderName);
                    break;
                }
            }
        }
    }

    @Transactional
    public void changeFavoriteFolderColor(String userName, String folderName, String color) {
        FavoriteCollection favoriteCollection = getFavoriteCollection(userName);
        if (favoriteCollection != null) {
            for (FavoriteFolder folder : favoriteCollection.getFavoriteFolders()) {
                if (folder.getName().equals(folderName)) {
                    folder.setColorHex(color);
                    break;
                }
            }
        }
    }

    private FavoriteCollection getFavoriteCollection(String userName) {
        Optional<UserData> optUser = userRepository.findByUserName(userName);
        if (optUser.isPresent()) {
            UserData user = optUser.get();

            FavoriteCollection favoriteCollection = user.getFavoriteCollection();
            if (favoriteCollection == null) {
                initFavorites(user);
                favoriteCollection = user.getFavoriteCollection();
            }
            return favoriteCollection;
        }
        return null;
    }

    public Set<FavoriteFolderModel> getFoldersByUserName(String userName) {
        Set<FavoriteFolderModel> folderModels = new HashSet<>();
        FavoriteCollection favoriteCollection = getFavoriteCollection(userName);
        if (favoriteCollection != null) {
            favoriteCollection.getFavoriteFolders()
                    .forEach(f -> folderModels.add(FavoriteFolderModel
                            .builder()
                            .name(f.getName())
                            .colorHex(f.getColorHex())
                            .build()));
        }
        return folderModels;
    }
}
