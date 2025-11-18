package com.statsproject.statsproject.repository;

import com.statsproject.statsproject.entity.BoxScore;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface BoxScoreRepository extends JpaRepository<BoxScore, Long> {
    List<BoxScore> findByGame_Id(Long gameId);
    List<BoxScore> findByCreatedBy(String username);
}
