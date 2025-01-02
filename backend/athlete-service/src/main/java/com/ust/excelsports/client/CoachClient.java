package com.ust.excelsports.client;

import com.ust.excelsports.dto.Coach;
import com.ust.excelsports.dto.CoachDto;
import com.ust.excelsports.model.CoachRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "COACH-SERVICE")
public interface CoachClient {

    @GetMapping("/api/v1/coaches")
    List<CoachDto> getAllCoaches();

    @GetMapping("/api/v1/coaches/{coachId}/requests")
    List<CoachRequest> getRequestsByCoachId(@PathVariable int coachId);

    @GetMapping("/api/v1/coaches/id/{id}")
    public Coach getCoachById(@PathVariable int id);

    @PostMapping("/api/v1/coaches/{coachId}/requests/{requestId}")
    ResponseEntity<String> respondToRequest(@PathVariable int coachId,
                                            @PathVariable Long requestId,
                                            @RequestParam("response") String response);

}

