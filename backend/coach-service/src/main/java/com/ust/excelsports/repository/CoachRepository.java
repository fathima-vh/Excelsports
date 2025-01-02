package com.ust.excelsports.repository;


import com.ust.excelsports.dto.CoachRequestsDto;
import com.ust.excelsports.model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoachRepository extends JpaRepository<Coach,Integer> {
    Coach findByEmail(String email);
    CoachRequestsDto findById(Long requestId);
}
