package com.ust.excelsports.service;

import com.ust.excelsports.dto.CalorieHistoryDTO;
import com.ust.excelsports.dto.CreateTSDto;
import com.ust.excelsports.dto.TrainingHistoryResponseDto;
import com.ust.excelsports.model.TrainingSessions;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TrainingSessionsService {

    TrainingSessions createTrainingSession(CreateTSDto trainingSession);
    TrainingSessions getTrainingSessionById(int id);
    List<TrainingSessions> getTrainingSessionsByAthleteId(int athleteId);
    List<TrainingSessions> getTrainingSessionsByDate(LocalDate date);
    List<TrainingSessions> getTrainingSessionsByAthleteAndDateRange(int athleteId, LocalDate startDate, LocalDate endDate);
    TrainingSessions updateTrainingSession(int id, TrainingSessions updatedTrainingSession);
    void deleteTrainingSession(int id);
     List<TrainingHistoryResponseDto> getTrainingHistoryForAthlete(int athleteId, Integer year, Integer month, Integer day);
     Optional<TrainingSessions> getLatestTrainingSessionByDate(int athleteId);

    List<Object[]> getCalorieHistory(int athleteId);
}
