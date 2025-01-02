package com.ust.excelsports.service;

import com.ust.excelsports.dto.Athlete;
import com.ust.excelsports.dto.CoachDetailsDto;
import com.ust.excelsports.dto.CoachRequestsDto;
import com.ust.excelsports.dto.CreateCoachDto;
import com.ust.excelsports.model.Coach;

import java.util.List;
import java.util.Optional;

public interface CoachService {
    Coach saveCoach(CreateCoachDto coach);
    List<CoachDetailsDto> getAllCoaches();
    Optional<Coach> getCoachById(int id);
    void deleteCoach(int id);
    Coach getCoachWithAthletes(int coachId);
    Coach updateCoach(int id,Coach coach);

    Coach getCoachByEmail(String email);

//    List<Athlete> getCoachWithAthletesByEmail(String email);
//    Optional<CoachRequestsDto> findById(Long requestId);
}

