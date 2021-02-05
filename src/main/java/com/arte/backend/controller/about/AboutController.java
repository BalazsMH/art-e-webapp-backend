package com.arte.backend.controller.about;

import com.arte.backend.model.about.AboutModel;
import com.arte.backend.service.about.AboutService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AboutController {
    private AboutService aboutService;

    @CrossOrigin
    @GetMapping("/about")
    public AboutModel aboutPage() {
        return aboutService.getAboutContent();
    }
}
