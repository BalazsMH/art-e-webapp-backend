package com.arte.backend.controller.browse;

import com.arte.backend.service.browse.BrowseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BrowseController {
    @Autowired
    BrowseService browseService;


    @GetMapping("/browse")
    public String renderBrowse() {
        return browseService.browse();
    }
}
