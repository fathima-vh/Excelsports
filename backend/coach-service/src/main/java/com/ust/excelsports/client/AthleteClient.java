package com.ust.excelsports.client;

import com.ust.excelsports.dto.Athlete;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "ATHLETE-SERVICE")
public interface AthleteClient {

    @GetMapping("/api/v1/athletes/id/{id}")
    public Athlete getAthleteById(@PathVariable int id);

    @PutMapping("api/v1/athletes/{id}")
    public Athlete updateAthlete(@PathVariable int id, @RequestBody Athlete athlete);

    @GetMapping("/api/v1/athletes/coaches/{coachId}")
    public List<Athlete> getAthletesByCoachId(@PathVariable int coachId);

//    @GetMapping("/api/v1/athletes")
//    public List<Athlete> findByCoachEmail(@PathVariable String coachEmail);
}
