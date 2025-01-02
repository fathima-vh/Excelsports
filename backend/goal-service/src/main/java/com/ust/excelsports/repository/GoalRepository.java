package com.ust.excelsports.repository;

import com.ust.excelsports.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Integer> {

    List<Goal> findByTargetCategoryIgnoreCase(String category);

    List<Goal> findByTargetExerciseNameIgnoreCase(String exerciseName);

    List<Goal> findByAthleteId(int athleteId);
}
