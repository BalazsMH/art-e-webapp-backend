package com.arte.backend.service.registration;

import com.arte.backend.model.database.entity.*;
import com.arte.backend.model.database.repository.UserRankRepository;
import com.arte.backend.model.database.repository.UserRepository;
import com.arte.backend.service.email.CustomEmailService;
import org.json.JSONObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

@Service
public class RegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomEmailService customEmailService;
    private final UserRankRepository userRankRepository;

    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder, CustomEmailService customEmailService, UserRankRepository userRankRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.customEmailService = customEmailService;
        this.userRankRepository = userRankRepository;
    }

    public JSONObject registerUser(String userName, String firstName, String lastName,
                                   String email, String password, String birthDate) {
        boolean emailAlreadyExists = userRepository.existsByEmail(email);
        boolean usernameAlreadyExists = userRepository.existsByUserName(userName);

        JSONObject response = new JSONObject();
        response.put("emailNotAvailable", emailAlreadyExists);
        response.put("usernameNotAvailable", usernameAlreadyExists);

        if (emailAlreadyExists || usernameAlreadyExists) { return response; }

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
