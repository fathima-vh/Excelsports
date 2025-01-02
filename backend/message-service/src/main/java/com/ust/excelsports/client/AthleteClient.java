package com.ust.excelsports.client;

import com.ust.excelsports.dto.Athlete;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ATHLETE-SERVICE")
public interface AthleteClient {

    @GetMapping("api/v1/athletes/{athleteId}/coach/{coachId}/validate")
    boolean isRelated(@PathVariable int athleteId,
                      @PathVariable int coachId);

    @GetMapping("/api/v1/athletes/coaches/{coachId}")
    public List<Athlete> getAthletesByCoachId(@PathVariable int coachId);

    @GetMapping("/api/v1/athletes/id/{id}")
    public Athlete getAthleteById(@PathVariable int id);
}
