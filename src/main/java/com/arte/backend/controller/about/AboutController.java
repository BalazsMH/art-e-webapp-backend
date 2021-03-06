package com.arte.backend.controller.about;

import com.arte.backend.model.about.AboutModel;
import com.arte.backend.service.about.AboutService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AboutController {
    private AboutService aboutService;

    @GetMapping("/about")
    public AboutModel aboutPage() {
        return aboutService.getAboutContent();
    }
}
