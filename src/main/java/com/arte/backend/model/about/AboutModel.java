package com.arte.backend.model.about;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class AboutModel {
    private String title;
    private String teamName;

    @Singular
    private List<String> teamMembers;
    private String description;

    @Singular
    private Map<String, String> licenses;
}
