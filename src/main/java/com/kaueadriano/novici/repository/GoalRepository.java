package com.kaueadriano.novici.repository;

import com.kaueadriano.novici.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal, Integer> {
}
