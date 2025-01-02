package com.ust.excelsports.client;

import com.ust.excelsports.dto.AthleteDto;
import com.ust.excelsports.model.Athlete;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ATHLETE-SERVICE")
public interface AthleteClient {

    @PostMapping("api/v1/athletes")
    public Athlete createAthlete(@RequestBody AthleteDto athlete);
}
