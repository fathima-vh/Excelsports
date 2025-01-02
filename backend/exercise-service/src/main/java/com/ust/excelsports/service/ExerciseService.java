package com.ust.excelsports.service;


import com.ust.excelsports.dto.ExerciseDto;
import com.ust.excelsports.dto.GetExerciseDto;
import com.ust.excelsports.model.Exercise;

import java.util.List;

public interface ExerciseService {

    Exercise createExercise(ExerciseDto exercise);
    Exercise getExerciseById(int id); // Internal use, if needed
    ExerciseDto getExerciseDtoById(int id); // For external responses
    Exercise getExerciseByName(String name); // Internal use, if needed
    ExerciseDto getExerciseDtoByName(String name); // For external responses
    List<Exercise> getAllExercises(); // Internal use, if needed
    List<ExerciseDto> getAllExerciseDtos(); // For external responses
    Exercise updateExercise(int id, Exercise updatedExercise);
    void deleteExercise(int id);
    List<Exercise> getExerciseByTrainingSessionId(int id);

}
