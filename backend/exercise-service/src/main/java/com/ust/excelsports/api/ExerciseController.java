package com.ust.excelsports.api;

import com.ust.excelsports.dto.ExerciseDto;
import com.ust.excelsports.dto.GetExerciseDto;
import com.ust.excelsports.model.Exercise;
import com.ust.excelsports.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exercises")
public class ExerciseController {

    @Autowired
    private ExerciseService exerciseService;

    // Create a new exercise
    @PostMapping
    public ResponseEntity<Exercise> createExercise(@RequestBody ExerciseDto exercise) {
        Exercise createdExercise = exerciseService.createExercise(exercise);
        return ResponseEntity.ok(createdExercise);
    }

    // Get exercise by ID
    @GetMapping("/{id}")
    public ResponseEntity<ExerciseDto> getExerciseById(@PathVariable int id) {
        return ResponseEntity.ok(exerciseService.getExerciseDtoById(id));
    }

    // Get exercise by name
    @GetMapping("/name/{name}")
    public ResponseEntity<ExerciseDto> getExerciseByName(@PathVariable String name) {
        return ResponseEntity.ok(exerciseService.getExerciseDtoByName(name));
    }

    // Get all exercises
    @GetMapping
    public ResponseEntity<List<ExerciseDto>> getAllExercises() {
        return ResponseEntity.ok(exerciseService.getAllExerciseDtos());
    }

    // Update an exercise
    @PutMapping("/{id}")
    public ResponseEntity<ExerciseDto> updateExercise(@PathVariable int id, @RequestBody Exercise exercise) {
        Exercise updatedExercise = exerciseService.updateExercise(id, exercise);
        return ResponseEntity.ok(new ExerciseDto(updatedExercise));
    }

    // Delete an exercise
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable int id) {
        exerciseService.deleteExercise(id);
        return ResponseEntity.noContent().build();
    }

    //get exercises by trainingsession-id

    @GetMapping("training-session-id/{id}")
    public ResponseEntity<List<Exercise>> getAllExercisebyTrainingSessionId(@PathVariable int id) {
        return ResponseEntity.ok(exerciseService.getExerciseByTrainingSessionId(id));
    }



}
