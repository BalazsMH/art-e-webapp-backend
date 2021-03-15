package com.arte.backend.service.favorites;

import com.arte.backend.model.database.entity.FavoriteCollection;
import com.arte.backend.model.database.entity.UserData;
import com.arte.backend.model.database.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FavoriteHelperTest {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void initDatabase() {
        print("Start DB init");
        UserData validUserWithCollection = UserData.builder()
                .id(1L)
                .birthDate(LocalDate.of(1980, 10, 12))
                .email("test@test.com")
                .firstName("Test")
                .lastName("User")
                .userName("TestUser")
                .build();

        userRepository.saveAndFlush(validUserWithCollection);
        print("User stored in DB");
    }

    @Test
    @Order(1)
    void getFavoriteCollection_validUserEmailWithoutCollection_returnAFavoriteCollection() {
        FavoriteHelper favoriteHelper = new FavoriteHelper(userRepository);
        UserData user = userRepository.findById(1L).get();

        FavoriteCollection expectedCollection = FavoriteCollection.builder().id(0L).build();
        FavoriteCollection actualCollection = favoriteHelper.getFavoriteCollection(user.getEmail());

        assertEquals(expectedCollection, actualCollection);
    }

    @Test
    @Order(2)
    void getFavoriteCollection_validUserEmailWithCollection_returnAFavoriteCollection() {
        FavoriteHelper favoriteHelper = new FavoriteHelper(userRepository);
        UserData user = userRepository.findById(2L).get();
        favoriteHelper.getFavoriteCollection(user.getEmail());

        FavoriteCollection expectedCollection = user.getFavoriteCollection();
        FavoriteCollection actualCollection = favoriteHelper.getFavoriteCollection(user.getEmail());

        assertEquals(expectedCollection, actualCollection);
    }

    @Test
    @Order(3)
    void getFavoriteCollection_invalidUserEmail_returnNull() {
        FavoriteHelper favoriteHelper = new FavoriteHelper(userRepository);
        String email = "noValidMail@test.com";

        FavoriteCollection actualCollection = favoriteHelper.getFavoriteCollection(email);

        assertNull(actualCollection);
    }

    private void print(String msg) {
        System.out.println(msg);
        System.out.flush();
    }
}