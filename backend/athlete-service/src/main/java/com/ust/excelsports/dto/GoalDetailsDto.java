package com.ust.excelsports.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoalDetailsDto {

    private String category;
    private Map<String, ExerciseSummary> categorizedExercises;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ExerciseSummary {
        private double currentValue;
        private double targetValue; // or any other relevant metric
    }

}
