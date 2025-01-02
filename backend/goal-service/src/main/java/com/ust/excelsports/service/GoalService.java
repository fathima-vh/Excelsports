package com.ust.excelsports.service;

import com.ust.excelsports.dto.GoalDto;
import com.ust.excelsports.model.Goal;

import java.util.List;
import java.util.Map;

public interface GoalService {

    Goal createGoal(int id,Goal goal);

    Goal getGoalById(int id);

    List<Goal> getGoalsByCategory(String category);

    List<Goal> getGoalsByExerciseName(String exerciseName);

    List<Goal> getGoalsByAthleteId(int athleteId);

    List<Goal> getAllGoals();

    Goal updateGoal(int id, Goal updatedGoal);

    void deleteGoal(int id);

    GoalDto convertToDto(Goal goal);

    Goal convertToEntity(GoalDto goalDto);

    Map<String, Map<String, Double>> calculateAthleteProgress(int athleteId);
}
