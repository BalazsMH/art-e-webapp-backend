package com.arte.backend.controller.browse;

import com.arte.backend.model.artpiece.ArtPieceModel;
import com.arte.backend.service.browse.MuseumApiDataProviderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "${frontend.address}")
public class BrowseController {
    private final MuseumApiDataProviderService museumApiDataProviderService;

    public BrowseController(MuseumApiDataProviderService museumApiDataProviderService) {
        this.museumApiDataProviderService = museumApiDataProviderService;
    }

    @GetMapping("/getArtData")
    public List<ArtPieceModel> returnArtData(@RequestParam(required = false, name = "q") String query,
                                             @RequestParam(required = false, name = "involvedMaker") String involvedMaker,
                                             @RequestParam(required = false, name = "technique") String technique,
                                             @RequestParam(required = false, name = "datingPeriod") String datingPeriod,
                                             @RequestParam(required = false, name = "p") String pageNumber,
                                             @RequestParam(required = false, name = "ps") String resultsPerPage,
                                             @RequestParam(required = false, name = "imgonly") Boolean imgOnly,
                                             @RequestParam(required = false, name = "culture") String culture) {

        return museumApiDataProviderService.getArtData(query, involvedMaker, technique, datingPeriod,
                pageNumber, resultsPerPage, imgOnly, culture);
    }
}
