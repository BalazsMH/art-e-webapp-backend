package com.arte.backend.service.registration;

import com.arte.backend.model.database.entity.UserData;
import com.arte.backend.model.database.entity.UserRole;
import com.arte.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;

@Service
@AllArgsConstructor
public class RegistrationService {
    UserRepository userRepository;

    public JSONObject registerUser(String userName, String firstName, String lastName,
                                   String email, String password, String birthDate) {

        System.out.println(userName + firstName + lastName + email + password + birthDate);

        boolean emailAlreadyExists = userRepository.existsByEmail(email);

        JSONObject response = new JSONObject();
        response.put("emailNotAvailable", emailAlreadyExists);

        if (emailAlreadyExists) {
            System.out.println("email already in use");
            return response;
        } else {
            System.out.println("email ok");
        }

        UserData user = UserData.builder()
                .userName(userName)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .registrationDate(LocalDate.now())
                .birthDate(LocalDate.parse(birthDate))
                .roles(Collections.singletonList(UserRole.USER))
                .build();
        //TODO:validate data and send response
        System.out.println(user.toString());
        userRepository.save(user);
        System.out.println("save user");
        return response;
    }
}
