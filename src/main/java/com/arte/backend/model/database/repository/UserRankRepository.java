package com.arte.backend.model.database.repository;

import com.arte.backend.model.database.entity.RankData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRankRepository extends JpaRepository<RankData, Long> {
    Optional<RankData> findById(Long id);
}
