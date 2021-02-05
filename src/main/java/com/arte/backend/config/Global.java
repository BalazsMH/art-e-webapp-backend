package com.arte.backend.config;

public class Global {
    public static final String API_KEY = "Gz1ZRsyI";
    public static final String API_RESPONSE_FORMAT = "json";
    public static final String API_COLLECTION_URL = String.format("https://www.rijksmuseum.nl/api/en/collection?key=%s&format=%s", API_KEY, API_RESPONSE_FORMAT);
    public static final String API_SINGLE_ARTWORK_URL = String.format("https://www.rijksmuseum.nl/api/en/collection", API_KEY);
}
