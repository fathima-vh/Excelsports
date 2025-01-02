package com.ust.excelsports.service;

import com.ust.excelsports.client.CategoryClient;
import com.ust.excelsports.client.ExerciseClient;
import com.ust.excelsports.client.TrainingSessionClient;
import com.ust.excelsports.dto.*;
import com.ust.excelsports.exception.DuplicateTrainingSession;
import com.ust.excelsports.model.TrainingSessions;
import com.ust.excelsports.repository.TrainingSessionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrainingSessionsServiceImpl implements TrainingSessionsService {

    @Autowired
    private TrainingSessionsRepository trainingSessionsRepository;

    @Autowired
    private ExerciseClient exerciseClient;

    @Autowired
    private TrainingSessionClient trainingSessionClient;

    @Autowired
    private CategoryClient categoryClient;

    @Override
    public TrainingSessions createTrainingSession(CreateTSDto trainingSession) {
        TrainingSessions saveTs = new TrainingSessions();

        // Check for existing training sessions for this athlete on the same date
        List<TrainingSessions> trainingSessions = getTrainingSessionsByDate(trainingSession.getDate());
        boolean exists = trainingSessions.stream()
                .anyMatch(ts -> ts.getAthleteId() == trainingSession.getAthleteId());

        if (exists) {
            throw new DuplicateTrainingSession("Training session already exists!!");
        }

        return trainingSessionsRepository.save(trainingSession.convertToTS(saveTs));
    }


    @Override
    public TrainingSessions getTrainingSessionById(int id) {
        Optional<TrainingSessions> trainingSession = trainingSessionsRepository.findById(id);
        if (trainingSession.isEmpty()) {
            throw new RuntimeException("Training session with ID " + id + " not found.");
        }
        List<ExerciseDto> exerciseDtoList = exerciseClient.getExercisesByTrainingSessionId(id);
        TrainingSessions session = trainingSession.get();
        session.setExercises(exerciseDtoList);
        return session;
    }

    @Override
    public List<TrainingSessions> getTrainingSessionsByAthleteId(int athleteId) {
        List<TrainingSessions> trainingSessionsList = trainingSessionsRepository.findByAthleteId(athleteId);
             trainingSessionsList.forEach(session -> {
            List<ExerciseDto> exerciseDtoList = exerciseClient.getExercisesByTrainingSessionId(session.getId());
            session.setExercises(exerciseDtoList);
            int totalCalorie=0;
                 for (ExerciseDto exerciseDto : exerciseDtoList) {
                     String exerciseName = exerciseDto.getExerciseName();
                     double currentValue = exerciseDto.getCurrentValue();

                     // Fetch the calories from the category table based on the exercise name
                     int exerciseCalories = categoryClient.getCalorie(exerciseName);  // Method to fetch calories

                     // Calculate calories burned for this exercise and add to total
                     totalCalorie += exerciseCalories * currentValue;
                 }
                 session.setCaloriesBurned(totalCalorie);
                 System.out.println("totalCalorie"+totalCalorie);
                 updateTrainingSession(session.getId(), session);

        });

        return trainingSessionsList;
    }


    @Override
    public List<TrainingSessions> getTrainingSessionsByDate(LocalDate date) {
        return trainingSessionsRepository.findByDate(date);
    }

    @Override
    public List<TrainingSessions> getTrainingSessionsByAthleteAndDateRange(int athleteId, LocalDate startDate, LocalDate endDate) {
        return trainingSessionsRepository.findByAthleteIdAndDateBetween(athleteId, startDate, endDate);
    }


    @Override
    public TrainingSessions updateTrainingSession(int id, TrainingSessions updatedTrainingSession) {
        TrainingSessions existingTrainingSession = getTrainingSessionById(id); // Ensure the training session exists

        // Update the fields only if they are not null or invalid
        if (updatedTrainingSession.getDate() != null) {
            existingTrainingSession.setDate(updatedTrainingSession.getDate());
        }
        if (updatedTrainingSession.getDuration() > 0) {
            existingTrainingSession.setDuration(updatedTrainingSession.getDuration());
        }
        if (updatedTrainingSession.getAthleteId() > 0) {
            existingTrainingSession.setAthleteId(updatedTrainingSession.getAthleteId());
        }

        // Save the updated entity
        return trainingSessionsRepository.save(existingTrainingSession);
    }

    @Override
    public void deleteTrainingSession(int id) {
        TrainingSessions existingTrainingSession = getTrainingSessionById(id); // Ensure the training session exists
        trainingSessionsRepository.delete(existingTrainingSession);
    }
    @Override
    public Optional<TrainingSessions> getLatestTrainingSessionByDate(int athleteId) {
        Optional<TrainingSessions> ts = trainingSessionsRepository.findTopByAthleteIdOrderByDateDesc(athleteId);
        List<ExerciseDto> exerciseDtoList = exerciseClient.getExercisesByTrainingSessionId(ts.get().getId());

        ts.get().setExercises(exerciseDtoList);
        return ts;
    }


@Override
public List<TrainingHistoryResponseDto> getTrainingHistoryForAthlete(int athleteId, Integer year, Integer month, Integer day) {
    // Fetch training sessions from training-session-service
    List<TrainingSessionDto> trainingSessions = trainingSessionClient.getTrainingSessionsByAthleteId(athleteId);

    // Filter based on year, month, and day if provided
    if (year != null || month != null || day != null) {
        trainingSessions = trainingSessions.stream()
                .filter(session -> {
                    LocalDate date = session.getDate();
                    return (year == null || date.getYear() == year) &&
                            (month == null || date.getMonthValue() == month) &&
                            (day == null || date.getDayOfMonth() == day);
                })
                .collect(Collectors.toList());
    }

    // Convert to DTO format
    return mapSessionsToDto(trainingSessions);
}

    private List<TrainingHistoryResponseDto> mapSessionsToDto(List<TrainingSessionDto> trainingSessions) {
        List<TrainingHistoryResponseDto> responseList = new ArrayList<>();

        for (TrainingSessionDto session : trainingSessions) {
            TrainingHistoryResponseDto response = new TrainingHistoryResponseDto();
            response.setSessionId(session.getId());
            response.setDate(session.getDate());
            response.setDuration(session.getDuration());
            response.setAthleteId(session.getAthleteId());

            // Fetch exercises for the session from exercise-service
            List<ExerciseDto> exercises = exerciseClient.getExercisesByTrainingSessionId(session.getId());

            // Initialize variable for total calories burned
            final int[] totalCaloriesBurned = {0};

            // Map exercises categorized by type and calculate calories burned
            Map<String, List<TrainingHistoryResponseDto.ExerciseSummary>> categorizedExercises = exercises.stream()
                    .collect(Collectors.groupingBy(
                            ExerciseDto::getCategory, // Grouping by exercise category
                            Collectors.mapping(exercise -> {
                                TrainingHistoryResponseDto.ExerciseSummary summary = new TrainingHistoryResponseDto.ExerciseSummary();
                                summary.setExerciseName(exercise.getExerciseName());
                                summary.setCurrentValue(exercise.getCurrentValue());

                                // Calculate calories burned for the exercise
                                int caloriesPerUnit = categoryClient.getCalorie(exercise.getExerciseName());
                                int caloriesBurned = caloriesPerUnit * exercise.getCurrentValue();
                                summary.setCaloriesBurned(caloriesBurned);

                                // Add to total calories burned for the session
                                totalCaloriesBurned[0] += caloriesBurned;

                                return summary;
                            }, Collectors.toList())
                    ));

            // Set the categorized exercises and total calories burned
            response.setCategorizedExercises(categorizedExercises);
            response.setTotalCaloriesBurned(totalCaloriesBurned[0]);

            responseList.add(response);
        }

        return responseList;
    }


//calender
    @Override
public List<Object[]> getCalorieHistory(int athleteId) {
    return trainingSessionsRepository.findTotalCaloriesByAthleteId(athleteId);
}
}
