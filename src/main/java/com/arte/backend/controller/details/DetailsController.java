package com.arte.backend.controller.details;

import com.arte.backend.service.details.ArtDetailsProviderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class DetailsController {
    ArtDetailsProviderService artDetailsProviderService;

    @GetMapping("/getArtDetails")
    public String returnArtDetails(@RequestParam(name = "objectNumber") String objectNumber) {
        return artDetailsProviderService.getArtDetails(objectNumber);
    }
}
