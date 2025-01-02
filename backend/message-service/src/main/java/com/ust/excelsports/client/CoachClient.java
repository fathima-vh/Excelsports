package com.ust.excelsports.client;

import com.ust.excelsports.dto.Coach;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "COACH-SERVICE")
public interface CoachClient {

    @GetMapping("/api/v1/coaches/id/{id}")
    public Coach getCoachById(@PathVariable int id);
}