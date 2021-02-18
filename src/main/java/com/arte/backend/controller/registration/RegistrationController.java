package com.arte.backend.controller.registration;

import com.arte.backend.model.database.entity.UserData;
import com.arte.backend.model.database.entity.UserRole;
import com.arte.backend.service.registration.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

@RestController
@AllArgsConstructor
public class RegistrationController {

    RegistrationService registrationService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/register")
    public String register(@RequestParam( name = "userName") String userName,
                           @RequestParam( name = "firstName") String firstName,
                           @RequestParam( name = "lastName") String lastName,
                           @RequestParam( name = "email") String email,
                           @RequestParam( name = "password") String password,
                           @RequestParam( name = "birthDate") String birthDate) {

        registrationService.registerUser(userName, firstName, lastName, email, password, birthDate);

        return null;
    }
}
