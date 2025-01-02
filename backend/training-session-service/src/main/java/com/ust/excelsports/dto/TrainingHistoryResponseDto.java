package com.ust.excelsports.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
public class TrainingHistoryResponseDto {
    private int sessionId;
    private LocalDate date;
    private int duration;
    private int athleteId;
    private int totalCaloriesBurned;  // Total calories burned in the session
    private Map<String, List<ExerciseSummary>> categorizedExercises; // Exercises categorized by type

    @Data
    public static class ExerciseSummary {
        private String exerciseName;
        private int currentValue;
        private double caloriesBurned;  // Calories burned for the specific exercise
    }
}
