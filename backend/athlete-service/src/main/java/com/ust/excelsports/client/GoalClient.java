package com.ust.excelsports.client;


import com.ust.excelsports.dto.GoalDetailsDto;
import com.ust.excelsports.dto.GoalDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "GOAL-SERVICE")
public interface GoalClient {

    @GetMapping("/api/v1/goals/athlete/{athleteId}")
    List<GoalDto> getGoalsByAthleteId(@PathVariable int athleteId);

    @DeleteMapping("/api/v1/goals")
    void deleteGoals(@RequestBody List<Integer> goalIds);


}
