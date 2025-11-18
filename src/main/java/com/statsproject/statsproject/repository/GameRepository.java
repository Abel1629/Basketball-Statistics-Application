package com.statsproject.statsproject.repository;

import com.statsproject.statsproject.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
public interface GameRepository extends JpaRepository<Game, Long> {
}
