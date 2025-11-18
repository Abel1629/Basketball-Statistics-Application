package com.statsproject.statsproject.repository;

import com.statsproject.statsproject.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TeamRepository extends JpaRepository<Team, Long> {
}
