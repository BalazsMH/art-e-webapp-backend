package com.arte.backend.service.registration;

import com.arte.backend.model.database.entity.*;
import com.arte.backend.repository.UserRankRepository;
import com.arte.backend.repository.UserRepository;
import com.arte.backend.service.email.CustomEmailService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RegistrationService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    CustomEmailService customEmailService;
    UserRankRepository userRankRepository;

    public JSONObject registerUser(String userName, String firstName, String lastName,
                                   String email, String password, String birthDate) {

        System.out.println(userName + firstName + lastName + email + password + birthDate);

        boolean emailAlreadyExists = userRepository.existsByEmail(email);

        JSONObject response = new JSONObject();
        response.put("emailNotAvailable", emailAlreadyExists);

        if (emailAlreadyExists) { return response; }

        Optional<RankData> rankDataOptional = userRankRepository.findById((long) 1);
        RankData rankData = null;
        if (rankDataOptional.isPresent()) {
            rankData = rankDataOptional.get();
        }

        UserStatistics userStatistics = UserStatistics.builder()
                .actualXp(0)
                .allAnswers(0)
                .correctAnswers(0)
                .dailyRemainingXp(1500)
                .dailyStreak(0)
                .winStreak(0)
                .rank(rankData)
                .build();

        UserData user = UserData.builder()
                .userName(userName)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(passwordEncoder.encode(password))
                .registrationDate(LocalDate.now())
                .birthDate(LocalDate.parse(birthDate))
                .roles(Collections.singletonList(UserRole.USER))
                .userStatistics(userStatistics)
                .build();
        //TODO:validate data and send response accordingly
        userRepository.save(user);
        customEmailService.sendHtmlConfirmationEmail(userName, email);
        return response;
    }
}
