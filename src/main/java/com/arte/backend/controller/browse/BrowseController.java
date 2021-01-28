package com.arte.backend.controller.browse;

import com.arte.backend.service.browse.BrowseService;
import com.arte.backend.service.browse.MuseumApiDataProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BrowseController {
    @Autowired
    BrowseService browseService;
    @Autowired
    MuseumApiDataProviderService museumApiDataProviderService;


    @GetMapping("/browse")
    public String renderBrowse() {
        return browseService.browse();
    }

    @GetMapping("/api/getArtData")
    public String returnArtData() {
        return museumApiDataProviderService.getArtData();
    }
}
