package com.ust.excelsports.api;

import com.ust.excelsports.dto.GoalDto;
import com.ust.excelsports.model.Goal;
import com.ust.excelsports.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/goals")
public class GoalController {

    @Autowired
    private GoalService goalService;

    @PostMapping("/athlete/{athleteId}")
    public ResponseEntity<Goal> createGoal(@PathVariable int athleteId,
                                           @RequestBody GoalDto goalDto) {
        Goal goal = goalService.convertToEntity(goalDto);
        return ResponseEntity.ok(goalService.createGoal(athleteId,goal));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoalDto> getGoalById(@PathVariable int id) {
        Goal goal = goalService.getGoalById(id);
        return ResponseEntity.ok(goalService.convertToDto(goal));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<GoalDto>> getGoalsByCategory(@PathVariable String category) {
        List<Goal> goals = goalService.getGoalsByCategory(category);
        List<GoalDto> goalDtos = goals.stream()
                .map(goalService::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(goalDtos);
    }

    @GetMapping("/exercise/{exerciseName}")
    public ResponseEntity<List<GoalDto>> getGoalsByExerciseName(@PathVariable String exerciseName) {
        List<Goal> goals = goalService.getGoalsByExerciseName(exerciseName);
        List<GoalDto> goalDtos = goals.stream()
                .map(goalService::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(goalDtos);
    }

    @GetMapping("/athlete/{athleteId}")
    public ResponseEntity<List<Goal>> getGoalsByAthleteId(@PathVariable int athleteId) {
        List<Goal> goals = goalService.getGoalsByAthleteId(athleteId);
//        List<GoalDto> goalDtos = goals.stream()
//                .map(goalService::convertToDto)
//                .collect(Collectors.toList());
        return ResponseEntity.ok(goals);
    }

    @GetMapping
    public ResponseEntity<List<Goal>> getAllGoals() {
        List<Goal> goals = goalService.getAllGoals();
        List<GoalDto> goalDtos = goals.stream()
                .map(goalService::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(goals);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Goal> updateGoal(@PathVariable int id, @RequestBody GoalDto goalDto) {
        Goal updatedGoal = goalService.convertToEntity(goalDto);
        return ResponseEntity.ok(goalService.updateGoal(id, updatedGoal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoal(@PathVariable int id) {
        goalService.deleteGoal(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/athlete/{athleteId}/progress")
    public ResponseEntity<Map<String, Map<String,Double>>> getProgressByAthleteId(
            @PathVariable int athleteId
    ){
        return ResponseEntity.ok(goalService.calculateAthleteProgress(athleteId));
    }
}
