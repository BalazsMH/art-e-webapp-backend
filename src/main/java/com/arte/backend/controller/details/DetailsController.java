package com.arte.backend.controller.details;

import com.arte.backend.service.details.ArtDetailsProviderService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class DetailsController {
    ArtDetailsProviderService artDetailsProviderService;

    public DetailsController(ArtDetailsProviderService artDetailsProviderService) {
        this.artDetailsProviderService = artDetailsProviderService;
    }

    @GetMapping("/getArtDetails")
    public String returnArtDetails(@RequestParam(name = "objectNumber") String objectNumber) {
        return artDetailsProviderService.getArtDetails(objectNumber);
    }
}
