package com.ust.excelsports.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CATEGORY-SERVICE")
public interface CategoryClient {

    @GetMapping("api/v1/category/exercise/{exercise}/calorie")
    int getCalorie(
            @PathVariable String exercise
    );
}
