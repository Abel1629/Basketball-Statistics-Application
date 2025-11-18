package com.statsproject.statsproject.repository;

import com.statsproject.statsproject.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findByHomeTeam_IdOrAwayTeam_Id(Long homeId, Long awayId);
}
