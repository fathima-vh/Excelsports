package com.ust.excelsports.client;

import com.ust.excelsports.dto.UserModelDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "AUTHENTICATION-SERVICE")
public interface AuthClient {

    @PostMapping("/api/v1/auth/register")
    void register(@RequestBody UserModelDto user);

}
