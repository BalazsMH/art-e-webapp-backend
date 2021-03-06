package com.arte.backend.service.favorites;

import com.arte.backend.model.database.entity.FavoriteCollection;
import com.arte.backend.model.database.entity.UserData;
import com.arte.backend.model.database.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class FavoriteHelper {
    private final UserRepository userRepository;

    public FavoriteHelper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public FavoriteCollection getFavoriteCollection(String userName) {
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
}
