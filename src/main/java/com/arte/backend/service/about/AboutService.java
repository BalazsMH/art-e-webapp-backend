package com.arte.backend.service.about;

import com.arte.backend.model.about.AboutModel;
import org.springframework.stereotype.Service;

@Service
public class AboutService {

    public AboutModel getAboutContent() {
        return AboutModel.builder()
                    .title("Art-E Web Application")
                    .teamName("Team Art-E")
                    .teamMember("Balázs Márton Horváth")
                    .teamMember("Dániel Méry")
                    .teamMember("László Miklós Vajay")
                    .description("This page was created as a team project for Codecool Advanced module. " +
                            "The main functionality is to get familiar with artworks of the Rijksmuseum. This is " +
                            "possible by browsing in the gallery and by taking several types of quiz. Answering correctly " +
                            "will earn experience to the actually logged in user. According to the earned experience, the user " +
                            "can check the actual position in the global scoreboard.")
                    .license("Rijksmuseum API", "https://www.rijksmuseum.nl/en/research/conduct-research/data/policy")
                    .license("React infinite scroll component", "https://github.com/ankeetmaini/react-infinite-scroll-component/blob/master/license")
                    .license("Font Awesome icon set", "https://fontawesome.com/license/free")
                    .license("Grommet", "https://github.com/grommet/grommet/blob/master/LICENSE")
                    .build();
    }
}
