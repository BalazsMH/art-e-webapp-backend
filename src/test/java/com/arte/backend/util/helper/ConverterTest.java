package com.arte.backend.util.helper;

import com.arte.backend.model.apiresponse.WebImage;
import com.arte.backend.model.artpiece.ArtPieceModel;
import com.arte.backend.model.favorites.FavoriteFolderModel;
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
        Set<ArtPieceModel> input = null;
        List<ArtPieceModel> actual = Converter.favoritesModelSetToSortedListByTitle(input);

        assertNull(actual);
    }

    @Test
    void favoritesModelSetToSortedListByTitle_inputFavoritesModelSet_returnListOrderedByLongTitle() {
        ArtPieceModel firstTestCase = ArtPieceModel.builder()
                .longTitle("First test case")
                .objectNumber("123")
                .webImage(mock(WebImage.class))
                .build();

        ArtPieceModel secondTestCase = ArtPieceModel.builder()
                .longTitle("Second test case")
                .objectNumber("456")
                .webImage(mock(WebImage.class))
                .build();

        ArtPieceModel lastTestCase = ArtPieceModel.builder()
                .longTitle("Last test case")
                .objectNumber("789")
                .webImage(mock(WebImage.class))
                .build();

        Set<ArtPieceModel> input = new HashSet<>(){{
            add(firstTestCase);
            add(secondTestCase);
            add(lastTestCase);
        }};

        List<ArtPieceModel> actual = Converter.favoritesModelSetToSortedListByTitle(input);

        List<ArtPieceModel> required = new ArrayList<>(){{
           add(firstTestCase);
           add(lastTestCase);
           add(secondTestCase);
        }};

        assertEquals(required, actual);
    }

    @Test
    void favoriteFolderModelSetToSortedListByName_inputNull_returnNull() {
        Set<FavoriteFolderModel> input = null;
        List<FavoriteFolderModel> actual = Converter.favoriteFolderModelSetToSortedListByName(input);

        assertNull(actual);
    }

    @Test
    void favoriteFolderModelSetToSortedListByName_inputFavoriteFolderModelSet_returnListOrderedByName() {
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