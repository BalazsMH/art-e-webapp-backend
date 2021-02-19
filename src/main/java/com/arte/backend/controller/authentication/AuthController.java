package com.arte.backend.controller.authentication;

import com.arte.backend.model.authentication.UserCredentials;
import com.arte.backend.repository.UserRepository;
import com.arte.backend.security.JwtTokenServices;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenServices jwtTokenServices;

    private final PasswordEncoder passwordEncoder;

    public AuthController(JwtTokenServices jwtTokenServices,
                          AuthenticationManager authenticationManager,
                          UserRepository users,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenServices = jwtTokenServices;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> login(@RequestParam(name = "password") String password,
                                        @RequestParam(name = "email") String email) {
        UserCredentials userCredentials = UserCredentials.builder().email(email).password(password).build();
        try {
            Authentication auth = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, userCredentials.getPassword()));

            List<String> roles = auth.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

//            String token = jwtTokenServices.createToken(email, roles);
//            Map<Object, Object> model = new HashMap<>();
//            model.put("email", email);
//            model.put("roles", roles);
//            model.put("token", token);
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Set-Cookie","username="+email+"; roles="+roles+"; token="+token+"; Max-Age=604800; Path=/; Secure; HttpOnly");

            //TODO:Send token in response
            JSONObject responseJson = new JSONObject();
            responseJson.put("loginSuccessful", true);


            return ResponseEntity.status(HttpStatus.OK).body(responseJson.toString());
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password provided");
        }
    }
}
