package com.ust.excelsports.client;

import com.ust.excelsports.dto.ExerciseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "EXERCISE-SERVICE")
public interface ExerciseClient {
    @GetMapping("api/v1/exercises/training-session-id/{trainingSessionId}")
    List<ExerciseDto> getExercisesByTrainingSessionId(@PathVariable int trainingSessionId);
}
