package com.arte.backend.model.database.repository;

import com.arte.backend.model.database.entity.RankData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankDataRepository extends JpaRepository<RankData, Long> {
    
}
