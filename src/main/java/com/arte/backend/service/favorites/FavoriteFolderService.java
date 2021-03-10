package com.arte.backend.service.favorites;

import com.arte.backend.model.database.entity.FavoriteCollection;
import com.arte.backend.model.database.entity.FavoriteFolder;
import com.arte.backend.model.favorites.FavoriteFolderModel;
import com.arte.backend.model.database.repository.UserRepository;
import com.arte.backend.util.helper.Converter;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FavoriteFolderService {
    private final UserRepository userRepository;
    private final FavoriteHelper favoriteHelper;

    public FavoriteFolderService(UserRepository userRepository, FavoriteHelper favoriteHelper) {
        this.userRepository = userRepository;
        this.favoriteHelper = favoriteHelper;
    }

    public void addFavoriteFolder(String email, String folderName, String colorHex) {
        FavoriteCollection favoriteCollection = favoriteHelper.getFavoriteCollection(email);
        if (favoriteCollection != null) {
            FavoriteFolder newFolder = FavoriteFolder.builder()
                    .name(folderName)
                    .colorHex(colorHex)
                    .favorites(new HashSet<>())
                    .build();

            favoriteCollection.getFavoriteFolders().add(newFolder);
            userRepository.save(userRepository.findByEmail(email).get());
        }
    }

    @Transactional
    public void deleteFavoriteFolder(String email, String folderName) {
        FavoriteCollection favoriteCollection = favoriteHelper.getFavoriteCollection(email);
        if (favoriteCollection != null) {
            favoriteCollection.getFavoriteFolders().removeIf(folder -> folder.getName().equals(folderName));
        }
    }

    @Transactional
    public void modifyFavoriteFolder(String email, String oldFolderName, String newFolderName, String newColor) {
        FavoriteCollection favoriteCollection = favoriteHelper.getFavoriteCollection(email);
        if (favoriteCollection != null) {
            for (FavoriteFolder folder : favoriteCollection.getFavoriteFolders()) {
                if (folder.getName().equals(oldFolderName)) {
                    folder.setName(newFolderName);
                    folder.setColorHex(newColor);
                    break;
                }
            }
        }
    }

    public List<FavoriteFolderModel> getFoldersByUserName(String email) {
        Set<FavoriteFolderModel> folderModels = new HashSet<>();
        FavoriteCollection favoriteCollection = favoriteHelper.getFavoriteCollection(email);
        if (favoriteCollection != null) {
            favoriteCollection.getFavoriteFolders()
                    .forEach(f -> folderModels.add(FavoriteFolderModel
                            .builder()
                            .id(f.getId())
                            .name(f.getName())
                            .colorHex(f.getColorHex())
                            .build()));
        }
        return Converter.favoriteFolderModelSetToSortedListByName(folderModels);
    }
}
