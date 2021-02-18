package com.arte.backend.repository;

import com.arte.backend.model.database.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserData, Long> {
    
}
