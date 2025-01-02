package com.ust.excelsports.service;

import com.ust.excelsports.dto.AthleteDto;
import com.ust.excelsports.dto.Coach;
import com.ust.excelsports.dto.CoachDto;
import com.ust.excelsports.dto.GoalDetailsDto;
import com.ust.excelsports.model.Athlete;

import java.util.List;

public interface AthleteService {

    Athlete createAthlete(AthleteDto athlete);
    Athlete updateAthlete(int id, Athlete updatedAthlete);
    Athlete getAthleteById(int id);
    Athlete getAthleteByEmail(String email);
    List<Athlete> getAthletesByCoachId(int coachId);
    void deleteAthlete(int id);

    List<CoachDto> getAllCoaches();
    List<GoalDetailsDto> getAthleteGoals(int athleteId);

    boolean isRelated(int athleteId,int coachId);

    public Coach getCoachByAthleteId(int id);

//    List<Athlete> findByCoachEmail(String coachEmail);
}
