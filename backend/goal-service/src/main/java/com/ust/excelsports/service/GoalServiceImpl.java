package com.ust.excelsports.service;

import com.ust.excelsports.client.TrainingSessionClient;
import com.ust.excelsports.dto.ExerciseDto;
import com.ust.excelsports.dto.GoalDto;
import com.ust.excelsports.dto.TrainingSessions;
import com.ust.excelsports.model.Goal;
import com.ust.excelsports.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GoalServiceImpl implements GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private TrainingSessionClient trainingSessionClient;

    @Override
    public Goal createGoal(int id,Goal goal) {
        goal.setAthleteId(id);
        return goalRepository.save(goal);
    }

    @Override
    public Goal getGoalById(int id) {
        Optional<Goal> goal = goalRepository.findById(id);
        if (goal.isEmpty()) {
            throw new RuntimeException("Goal with ID " + id + " not found.");
        }
        return goal.get();
    }

    @Override
    public List<Goal> getGoalsByCategory(String category) {
        return goalRepository.findByTargetCategoryIgnoreCase(category);
    }

    @Override
    public List<Goal> getGoalsByExerciseName(String exerciseName) {
        return goalRepository.findByTargetExerciseNameIgnoreCase(exerciseName);
    }

    @Override
    public List<Goal> getGoalsByAthleteId(int athleteId) {
        return goalRepository.findByAthleteId(athleteId);
    }

    @Override
    public List<Goal> getAllGoals() {
        return goalRepository.findAll();
    }

    @Override
    public Goal updateGoal(int id, Goal updatedGoal) {
        Goal existingGoal = getGoalById(id); // Ensure the goal exists

        // Check and update each field if it's provided
        if (updatedGoal.getTargetCategory() != null) {
            existingGoal.setTargetCategory(updatedGoal.getTargetCategory());
        }
        if (updatedGoal.getTargetExerciseName() != null) {
            existingGoal.setTargetExerciseName(updatedGoal.getTargetExerciseName());
        }
        if (updatedGoal.getTargetValue() > 0) { // Assuming 0 means "not provided"
            existingGoal.setTargetValue(updatedGoal.getTargetValue());
        }

        return goalRepository.save(existingGoal);
    }

    @Override
    public void deleteGoal(int id) {
        Goal existingGoal = getGoalById(id); // Ensure the goal exists
        goalRepository.delete(existingGoal);
    }

    @Override
    public GoalDto convertToDto(Goal goal) {
        GoalDto goalDto = new GoalDto();
        goalDto.setTargetCategory(goal.getTargetCategory());
        goalDto.setTargetExerciseName(goal.getTargetExerciseName());
        goalDto.setTargetValue(goal.getTargetValue());
        return goalDto;
    }

    @Override
    public Goal convertToEntity(GoalDto goalDto) {
        Goal goal = new Goal();
        goal.setTargetCategory(goalDto.getTargetCategory());
        goal.setTargetExerciseName(goalDto.getTargetExerciseName());
        goal.setTargetValue(goalDto.getTargetValue());
        return goal;
    }



    //progress

    @Override
    public Map<String, Map<String, Double>> calculateAthleteProgress(int athleteId) {
        // Step 1: Fetch goals for the athlete
        List<Goal> goals = getGoalsByAthleteId(athleteId);

        // Step 2: Fetch all training sessions for the athlete
        List<TrainingSessions> trainingSessions = trainingSessionClient.getTrainingSessionsByAthleteId(athleteId);

        // Step 3: Create a cumulative progress map to track actual values for exercises
        Map<String, Map<String, Integer>> cumulativeActualValuesMap = new HashMap<>();

        // Initialize cumulative actual values for each goal
        for (Goal goal : goals) {
            String category = goal.getTargetCategory();
            String exerciseName = goal.getTargetExerciseName();
            cumulativeActualValuesMap
                    .computeIfAbsent(category, k -> new HashMap<>())
                    .put(exerciseName, 0); // Initial value is 0 for all exercises
        }

        // Step 4: Calculate cumulative actual values for each exercise based on the training sessions
        for (TrainingSessions session : trainingSessions) {
            for (ExerciseDto exercise : session.getExercises()) {
                String exerciseCategory = exercise.getCategory();
                String exerciseName = exercise.getExerciseName();
                int actualValue = exercise.getCurrentValue();

                // Check if the exercise category and name match any goal
                for (Goal goal : goals) {
                    if (goal.getTargetCategory().equals(exerciseCategory) && goal.getTargetExerciseName().equals(exerciseName)) {
                        // Retrieve the current cumulative actual value for this exercise
                        Map<String, Integer> exerciseCumulativeMap = cumulativeActualValuesMap.get(exerciseCategory);

                        // Update the cumulative actual value
                        int currentValue = exerciseCumulativeMap.getOrDefault(exerciseName, 0);
                        exerciseCumulativeMap.put(exerciseName, currentValue + actualValue);
                        break;
                    }
                }
            }
        }

        // Step 5: Convert cumulative actual values to progress percentages
        Map<String, Map<String, Double>> progressMap = new HashMap<>();

        for (Goal goal : goals) {
            String category = goal.getTargetCategory();
            String exerciseName = goal.getTargetExerciseName();

            int actualValue = cumulativeActualValuesMap
                    .getOrDefault(category, new HashMap<>())
                    .getOrDefault(exerciseName, 0);

            double progress = (double) actualValue / goal.getTargetValue() * 100;

            // Ensure progress does not exceed 100%
            progressMap
                    .computeIfAbsent(category, k -> new HashMap<>())
                    .put(exerciseName, Math.min(progress, 100.0));
        }

        return progressMap;
    }

}
