package com.ust.excelsports.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoalDto {
    private int id;
    private int athleteId;
    private String targetCategory;
    private String targetExerciseName;
    private double targetValue;
}
