package com.arte.backend.service.registration;

import com.arte.backend.model.database.entity.UserData;
import com.arte.backend.model.database.entity.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;

@Service
@AllArgsConstructor
public class RegistrationService {

    public void registerUser(String userName, String firstName, String lastName,
                             String email, String password, String birthDate) {
        System.out.println(userName + firstName + lastName + email + password + birthDate);
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
        System.out.println(user.toString());
    }
}
