package com.ust.excelsports.client;

import com.ust.excelsports.dto.CreateCoachDto;
import com.ust.excelsports.model.Coach;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "COACH-SERVICE")
public interface CoachClient {

    @PostMapping("api/v1/coaches")
    public Coach createCoach(@RequestBody CreateCoachDto createCoachDto);

}

