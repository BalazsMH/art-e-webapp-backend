package com.arte.backend.controller.about;

import com.arte.backend.model.about.AboutModel;
import com.arte.backend.service.about.AboutService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AboutController {
    private final AboutService aboutService;

    public AboutController(AboutService aboutService) {
        this.aboutService = aboutService;
    }

    @GetMapping("/about")
    public AboutModel aboutPage() {
        return aboutService.getAboutContent();
    }
}
