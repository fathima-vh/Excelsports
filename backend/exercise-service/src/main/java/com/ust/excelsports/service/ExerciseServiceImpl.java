package com.ust.excelsports.service;

import com.ust.excelsports.dto.ExerciseDto;
import com.ust.excelsports.dto.GetExerciseDto;
import com.ust.excelsports.model.Exercise;
import com.ust.excelsports.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Override
    public Exercise createExercise(ExerciseDto exercise) {
        Exercise saveExercise = new Exercise();
        return exerciseRepository.save(exercise.convertDto(saveExercise));
    }

    @Override
    public Exercise getExerciseById(int id) {
        return exerciseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercise with ID " + id + " not found."));
    }

    @Override
    public ExerciseDto getExerciseDtoById(int id) {
        return new ExerciseDto(getExerciseById(id)); // Convert to DTO
    }

    @Override
    public Exercise getExerciseByName(String name) {
        Exercise exercise = exerciseRepository.findByExerciseName(name);
        if (exercise == null) {
            throw new RuntimeException("Exercise with name " + name + " not found.");
        }
        return exercise;
    }

    @Override
    public ExerciseDto getExerciseDtoByName(String name) {
        return new ExerciseDto(getExerciseByName(name)); // Convert to DTO
    }

    @Override
    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    @Override
    public List<ExerciseDto> getAllExerciseDtos() {
        return getAllExercises().stream().map(ExerciseDto::new).collect(Collectors.toList());
    }

    @Override
    public Exercise updateExercise(int id, Exercise updatedExercise) {
        Exercise existingExercise = getExerciseById(id);

        if (updatedExercise.getExerciseName() != null) {
            existingExercise.setExerciseName(updatedExercise.getExerciseName());
        }
        if (updatedExercise.getCategory() != null) {
            existingExercise.setCategory(updatedExercise.getCategory());
        }
        if (updatedExercise.getCurrentValue() != 0) {
            existingExercise.setCurrentValue(updatedExercise.getCurrentValue());
        }

        return exerciseRepository.save(existingExercise);
    }

    @Override
    public void deleteExercise(int id) {
        Exercise existingExercise = getExerciseById(id);
        exerciseRepository.delete(existingExercise);
    }

    @Override
    public List<Exercise> getExerciseByTrainingSessionId(int id) {
        return exerciseRepository.findByTrainingSessionsId(id);
    }
}
