package com.arte.backend.util.helper;

import com.arte.backend.model.favorites.FavoriteFolderModel;
import com.arte.backend.model.favorites.FavoritesModel;

import java.util.*;
import java.util.stream.Collectors;

public class Converter {
    public static List<FavoritesModel> favoritesModelSetToSortedListByTitle(Set<FavoritesModel> inputSet) {
        if (inputSet == null) { return null; }
        List<FavoritesModel> resultList = new ArrayList<>(inputSet);
        resultList = resultList.stream()
                .sorted(Comparator.comparing(FavoritesModel::getLongTitle))
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
