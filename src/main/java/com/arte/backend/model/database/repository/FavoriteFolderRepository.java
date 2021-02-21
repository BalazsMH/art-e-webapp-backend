package com.arte.backend.model.database.repository;

import com.arte.backend.model.database.entity.FavoriteFolder;
import com.arte.backend.model.database.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteFolderRepository extends JpaRepository<FavoriteFolder, Long> {

    List<FavoriteFolder> findAllByUser(UserData user);

    FavoriteFolder findByUserAndName(UserData user, String name);
}
