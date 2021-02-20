package com.arte.backend.repository;

import com.arte.backend.model.database.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserData, Long> {

    Optional<UserData> findByUserName(String username);

    Optional<UserData> findByEmail(String emailAddress);

    boolean existsByEmail(String email);
}
