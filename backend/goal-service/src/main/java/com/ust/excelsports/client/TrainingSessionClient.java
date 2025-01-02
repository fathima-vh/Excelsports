package com.ust.excelsports.client;

import com.ust.excelsports.dto.TrainingSessions;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "training-session-service")
public interface TrainingSessionClient {

    @GetMapping("/api/v1/training-sessions/athlete/{athleteId}")
    List<TrainingSessions> getTrainingSessionsByAthleteId(@PathVariable int athleteId);
}
