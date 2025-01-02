package com.ust.excelsports.repository;

import com.ust.excelsports.dto.CalorieHistoryDTO;
import com.ust.excelsports.model.TrainingSessions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TrainingSessionsRepository extends JpaRepository<TrainingSessions, Integer> {

    List<TrainingSessions> findByDate(LocalDate date);
    List<TrainingSessions> findByAthleteIdAndDateBetween(int athleteId, LocalDate startDate, LocalDate endDate);
    List<TrainingSessions> findByAthleteId(int athleteId);
    Optional<TrainingSessions> findTopByAthleteIdOrderByDateDesc(int athleteId);

    boolean existsByDate(LocalDate date);

    //for calender

    @Query("SELECT ts.date, SUM(ts.caloriesBurned) FROM TrainingSessions ts WHERE ts.athleteId = :athleteId GROUP BY ts.date")
    List<Object[]> findTotalCaloriesByAthleteId(@Param("athleteId") int athleteId);

}
