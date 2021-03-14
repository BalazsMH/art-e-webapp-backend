package com.arte.backend.util.helper;

import com.arte.backend.model.apiresponse.WebImage;
import com.arte.backend.model.favorites.FavoriteFolderModel;
import com.arte.backend.model.favorites.FavoritesModel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
class ConverterTest {

    @Test
    void favoritesModelSetToSortedListByTitle_inputNull_returnNull() {
        Set<FavoritesModel> input = null;
        List<FavoritesModel> actual = Converter.favoritesModelSetToSortedListByTitle(input);

        assertNull(actual);
    }

    @Test
    void favoritesModelSetToSortedListByTitle_inputFavoritesModelSet_returnListOrderedByLongTitle() {
        FavoritesModel firstTestCase = FavoritesModel.builder()
                .longTitle("First test case")
                .objectNumber("123")
                .headerImage(mock(WebImage.class))
                .build();

        FavoritesModel secondTestCase = FavoritesModel.builder()
                .longTitle("Second test case")
                .objectNumber("456")
                .headerImage(mock(WebImage.class))
                .build();

        FavoritesModel lastTestCase = FavoritesModel.builder()
                .longTitle("Last test case")
                .objectNumber("789")
                .headerImage(mock(WebImage.class))
                .build();

        Set<FavoritesModel> input = new HashSet<>(){{
            add(firstTestCase);
            add(secondTestCase);
            add(lastTestCase);
        }};

        List<FavoritesModel> actual = Converter.favoritesModelSetToSortedListByTitle(input);

        List<FavoritesModel> required = new ArrayList<>(){{
           add(firstTestCase);
           add(lastTestCase);
           add(secondTestCase);
        }};

        assertEquals(required, actual);
    }

    @Test
    void favoriteFolderModelSetToSortedListByName() {
    }

    @Test
    void favoriteFolderModelSetToSortedListByName_inputNull_returnNull() {
        Set<FavoriteFolderModel> input = null;
        List<FavoriteFolderModel> actual = Converter.favoriteFolderModelSetToSortedListByName(input);

        assertNull(actual);
    }

    @Test
    void favoriteFolderModelSetToSortedListByName_inputFavoriteFolderModelSet_returnListOrderedByLongTitle() {
        FavoriteFolderModel firstTestCase = FavoriteFolderModel.builder()
                .name("First test case")
                .id(1L)
                .colorHex("ffffff")
                .build();

        FavoriteFolderModel secondTestCase = FavoriteFolderModel.builder()
                .name("Second test case")
                .id(2L)
                .colorHex("0A0A0A")
                .build();

        FavoriteFolderModel lastTestCase = FavoriteFolderModel.builder()
                .name("Last test case")
                .id(3L)
                .colorHex("000000")
                .build();


        Set<FavoriteFolderModel> input = new HashSet<>(){{
            add(firstTestCase);
            add(secondTestCase);
            add(lastTestCase);
        }};

        List<FavoriteFolderModel> actual = Converter.favoriteFolderModelSetToSortedListByName(input);

        List<FavoriteFolderModel> required = new ArrayList<>(){{
            add(firstTestCase);
            add(lastTestCase);
            add(secondTestCase);
        }};

        assertEquals(required, actual);
    }
}