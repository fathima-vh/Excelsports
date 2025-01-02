package com.ust.excelsports.client;

import com.ust.excelsports.dto.TrainingSessionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "TRAINING-SESSION-SERVICE")
public interface TrainingSessionClient {

    @GetMapping("/api/v1/training-sessions/athlete/{athleteId}")
    List<TrainingSessionDto> getTrainingSessionsByAthleteId(@PathVariable("athleteId") int athleteId);
}
