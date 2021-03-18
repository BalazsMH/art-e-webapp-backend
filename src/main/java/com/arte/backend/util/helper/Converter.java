package com.arte.backend.util.helper;

import com.arte.backend.model.favorites.FavoriteFolderModel;
import com.arte.backend.model.artpiece.ArtPieceModel;

import java.util.*;
import java.util.stream.Collectors;

public class Converter {
    public static List<ArtPieceModel> favoritesModelSetToSortedListByTitle(Set<ArtPieceModel> inputSet) {
        if (inputSet == null) { return null; }
        List<ArtPieceModel> resultList = new ArrayList<>(inputSet);
        resultList = resultList.stream()
                .sorted(Comparator.comparing(ArtPieceModel::getLongTitle))
                .collect(Collectors.toList());
        return resultList;
    }

    public static List<FavoriteFolderModel> favoriteFolderModelSetToSortedListByName(Set<FavoriteFolderModel> inputSet) {
        if (inputSet == null) { return null; }
        List<FavoriteFolderModel> resultList = new ArrayList<>(inputSet);
        resultList = resultList.stream()
                .sorted(Comparator.comparing(FavoriteFolderModel::getName))
                .collect(Collectors.toList());
        return resultList;
    }
}
