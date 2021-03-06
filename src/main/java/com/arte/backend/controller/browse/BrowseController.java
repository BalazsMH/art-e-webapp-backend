package com.arte.backend.controller.browse;

import com.arte.backend.service.browse.MuseumApiDataProviderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class BrowseController {
    MuseumApiDataProviderService museumApiDataProviderService;

    //TODO:create pararmeter to allow CORS only from frontend app
    @GetMapping("/getArtData")
    public String returnArtData(@RequestParam(required = false, name = "q") String query,
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
