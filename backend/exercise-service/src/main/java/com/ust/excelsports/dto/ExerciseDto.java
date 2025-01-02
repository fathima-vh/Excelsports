package com.ust.excelsports.dto;

import com.ust.excelsports.model.Exercise;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseDto {

    private String exerciseName;

    private int currentValue;

    private String category;
    private int trainingSessionsId;


    public ExerciseDto(Exercise exercise) {
        this.exerciseName = exercise.getExerciseName();
        this.currentValue = exercise.getCurrentValue();
        this.category = exercise.getCategory() != null ? exercise.getCategory().toString() : null;
        this.trainingSessionsId=exercise.getTrainingSessionsId();
    }

    public Exercise convertDto(Exercise exercise){
        exercise.setExerciseName(this.exerciseName);
        exercise.setCurrentValue(this.currentValue);
        exercise.setCategory(this.category);
        exercise.setTrainingSessionsId(this.trainingSessionsId);
        return exercise;
    }
}
