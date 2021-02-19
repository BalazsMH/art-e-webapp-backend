package com.arte.backend.controller.registration;

import com.arte.backend.service.registration.RegistrationService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RegistrationController {

    RegistrationService registrationService;


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(@RequestParam( name = "userName") String userName,
                                   @RequestParam( name = "firstName") String firstName,
                                   @RequestParam( name = "lastName") String lastName,
                                   @RequestParam( name = "email") String email,
                                   @RequestParam( name = "password") String password,
                                   @RequestParam( name = "birthDate") String birthDate) {

        JSONObject responseJson = registrationService.registerUser(userName, firstName, lastName, email, password, birthDate);

        return ResponseEntity.ok().body(responseJson.toString());
    }
}
