package com.arte.backend.repository;

import com.arte.backend.model.database.entity.UserStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserStatisticsRepository extends JpaRepository<UserStatistics, Long> {

    Optional<UserStatistics> findById(Long id);
}
