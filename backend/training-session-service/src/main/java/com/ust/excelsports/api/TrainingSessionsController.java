package com.ust.excelsports.api;

import com.ust.excelsports.dto.CalorieHistoryDTO;
import com.ust.excelsports.dto.CreateTSDto;
import com.ust.excelsports.dto.TrainingHistoryResponseDto;
import com.ust.excelsports.model.TrainingSessions;
import com.ust.excelsports.service.TrainingSessionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/training-sessions")
public class TrainingSessionsController {

    @Autowired
    private TrainingSessionsService trainingSessionsService;

    @PostMapping
    public ResponseEntity<TrainingSessions> createTrainingSession(@RequestBody CreateTSDto trainingSession) {
        TrainingSessions trainingSessions =trainingSessionsService.createTrainingSession(trainingSession);
        return ResponseEntity.ok().body(trainingSessions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingSessions> getTrainingSessionById(@PathVariable int id) {
        return ResponseEntity.ok(trainingSessionsService.getTrainingSessionById(id));
    }

    @GetMapping("/athlete/{athleteId}")
    public ResponseEntity<List<TrainingSessions>> getTrainingSessionsByAthleteId(@PathVariable int athleteId) {
        return ResponseEntity.ok(trainingSessionsService.getTrainingSessionsByAthleteId(athleteId));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<TrainingSessions>> getTrainingSessionsByDate(@PathVariable LocalDate date) {
        return ResponseEntity.ok(trainingSessionsService.getTrainingSessionsByDate(date));
    }

    @GetMapping("/athlete/{athleteId}/date-range")
    public ResponseEntity<List<TrainingSessions>> getTrainingSessionsByAthleteAndDateRange(
            @PathVariable int athleteId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(trainingSessionsService.getTrainingSessionsByAthleteAndDateRange(athleteId, startDate, endDate));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<TrainingSessions> updateTrainingSession(@PathVariable int id, @RequestBody TrainingSessions trainingSession) {
        return ResponseEntity.ok(trainingSessionsService.updateTrainingSession(id, trainingSession));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainingSession(@PathVariable int id) {
        trainingSessionsService.deleteTrainingSession(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/athlete/{athleteId}/training-history")
    public ResponseEntity<List<TrainingHistoryResponseDto>> getTrainingByAthleteId(@PathVariable int athleteId ,@RequestParam(required = true) Integer year,
                                                                                   @RequestParam(required = false) Integer month,
                                                                                   @RequestParam(required = false) Integer day) {
        List<TrainingHistoryResponseDto> history = trainingSessionsService.getTrainingHistoryForAthlete(athleteId, year, month, day);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/athlete/{athleteId}/latest-training-session")
    public ResponseEntity<TrainingSessions> getLatest(
            @PathVariable int athleteId
    ){
        return ResponseEntity.ok().body(trainingSessionsService.getLatestTrainingSessionByDate(athleteId).get());
    }


    //calender

    @GetMapping("/athlete/{athleteId}/calorie-history")
    public List<Object[]> getCalorieHistory(@PathVariable int athleteId) {
        return trainingSessionsService.getCalorieHistory(athleteId);
    }
}
