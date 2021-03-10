package com.arte.backend.util.quiz;

public enum QuizType {
    DETAIL("detail"),
    TITLE("title"),
    MAKER("maker");

    private final String name;

    QuizType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
