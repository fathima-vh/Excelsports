package com.ust.excelsports.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseDto {

    private int id;
    private String exerciseName;
    private int currentValue;
    private String category;
    private int goalId;

}
