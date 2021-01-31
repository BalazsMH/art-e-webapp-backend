package com.arte.backend.controller.details;

import com.arte.backend.service.details.ArtDetailsProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DetailsController {

    @Autowired
    ArtDetailsProviderService artDetailsProviderService;

    @CrossOrigin
    @GetMapping("/api/getArtDetails")
    public String returnArtDetails(@RequestParam(required = true, name = "objectNumber") String objectNumber) {
        return artDetailsProviderService.getArtDetails(objectNumber);
    }

}
