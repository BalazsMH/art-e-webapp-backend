package com.arte.backend.service.favorites;

import com.arte.backend.model.database.entity.FavoriteCollection;
import com.arte.backend.model.database.entity.FavoriteFolder;
import com.arte.backend.model.database.entity.UserData;
import com.arte.backend.model.database.repository.UserRepository;
import com.arte.backend.model.favorites.FavoriteFolderModel;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FavoriteFolderServiceTest {
    @Autowired
    private UserRepository userRepository;

    private FavoriteFolderService favoriteFolderService;

    private final String email = "test@test.com";

    @Test
    @Order(1)
    public void getFoldersByEmail_validUserFavoriteFolders_returnUsersFavoriteFolders() {
        List<FavoriteFolderModel> favoriteFolders = favoriteFolderService.getFoldersByEmail(email);

        assertEquals(new ArrayList<FavoriteFolderModel>(), favoriteFolders);
    }

    @Test
    @Order(2)
    public void addFavoriteFolder_validUser_returnUsersFavoriteFoldersWithNewFolder() {
        String folderName = "testFolder";
        String colorHex = "123456";
        List<FavoriteFolderModel> expectedFolders = new ArrayList<>() {{
            add(FavoriteFolderModel.builder()
                    .name(folderName)
                    .colorHex(colorHex)
                    .id(3L)
                    .build());
        }};

        favoriteFolderService.addFavoriteFolder(email, folderName, colorHex);
        List<FavoriteFolderModel> favoriteFolders = favoriteFolderService.getFoldersByEmail(email);

        assertEquals(expectedFolders, favoriteFolders);
    }

    @Test
    @Order(3)
    public void modifyFavoriteFolder_validUser_returnUsersFavoriteFoldersWithModifiedFolder() {
        String folderName = "testFolder";
        String colorHex = "123456";
        favoriteFolderService.addFavoriteFolder(email, folderName, colorHex);
        String newFolderName = "modifiedFolder";
        String newColorHex = "654321";
        List<FavoriteFolderModel> expectedFolders = new ArrayList<>() {{
            add(FavoriteFolderModel.builder()
                    .name(newFolderName)
                    .colorHex(newColorHex)
                    .id(4L)
                    .build());
        }};

        favoriteFolderService.modifyFavoriteFolder(email, folderName, newFolderName, newColorHex);
        List<FavoriteFolderModel> favoriteFolders = favoriteFolderService.getFoldersByEmail(email);

        assertEquals(expectedFolders, favoriteFolders);
    }

    @Test
    @Order(4)
    public void deleteFavoriteFolder_validUser_returnUsersFavoriteFoldersWithoutDeletedFolder() {
        String folderName = "modifiedFolder";

        favoriteFolderService.deleteFavoriteFolder(email, folderName);
        List<FavoriteFolderModel> favoriteFolders = favoriteFolderService.getFoldersByEmail(email);

        assertEquals(new ArrayList<FavoriteFolderModel>(), favoriteFolders);
    }

    @BeforeAll
    public void initDatabase() {
        favoriteFolderService = new FavoriteFolderService(userRepository, new FavoriteHelper(userRepository));

        FavoriteFolder favoriteFolder = FavoriteFolder.builder()
                .colorHex("234567")
                .name("just a folder")
                .build();

        FavoriteCollection favoriteCollection = FavoriteCollection.builder()
                .favoriteFolders(new HashSet<>() {{
                    add(favoriteFolder);
                }})
                .favorites(new HashSet<>())
                .build();

        UserData validUserWithCollection = UserData.builder()
                .id(1L)
                .birthDate(LocalDate.of(1980, 10, 12))
                .email(email)
                .firstName("Test")
                .lastName("User")
                .userName("TestUser")
                .favoriteCollection(favoriteCollection)
                .build();

        userRepository.saveAndFlush(validUserWithCollection);
    }
}