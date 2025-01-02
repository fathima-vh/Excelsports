package com.ust.excelsports.service;

import com.ust.excelsports.client.AuthClient;
import com.ust.excelsports.client.CoachClient;
import com.ust.excelsports.client.GoalClient;
import com.ust.excelsports.client.TrainingSessionClient;
import com.ust.excelsports.dto.*;
import com.ust.excelsports.exception.AthleteNotFoundException;
import com.ust.excelsports.exception.DuplicateAthleteException;
import com.ust.excelsports.model.Athlete;
import com.ust.excelsports.model.RoleEnum;
import com.ust.excelsports.repository.AthleteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AthleteServiceImpl implements AthleteService {

    @Autowired
    private AthleteRepository athleteRepository;

    @Autowired
    private CoachClient coachClient;

    @Autowired
    private GoalClient goalClient;

    @Autowired
    private TrainingSessionClient trainingSessionClient;

    @Autowired
    private AuthClient authClient;

    @Override
    public Athlete createAthlete(AthleteDto athlete) {

        if(athleteRepository.findByEmail(athlete.getEmail())!=null){
            throw new DuplicateAthleteException("Email already exists!!");
        }
        Athlete savedAthlete = new Athlete();
        savedAthlete.setName(athlete.getName());
        savedAthlete.setHeight(athlete.getHeight());
        savedAthlete.setEmail(athlete.getEmail());
        savedAthlete.setAge(athlete.getAge());
        savedAthlete.setSport(athlete.getSport());
        savedAthlete.setWeight(athlete.getWeight());
        savedAthlete.setDob(athlete.getDob());
        savedAthlete.setPassword(athlete.getPassword());
        savedAthlete.setRole(RoleEnum.ATHLETE);

        return athleteRepository.save(savedAthlete);
    }

    @Override
    public Athlete updateAthlete(int id, Athlete updatedAthlete) {
        // Fetch existing athlete
        Athlete existingAthlete = athleteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Athlete with ID " + id + " not found"));

        // Update fields only if provided in the updated athlete object
        if (updatedAthlete.getName() != null) {
            existingAthlete.setName(updatedAthlete.getName());
        }
        if (updatedAthlete.getEmail() != null) {
            existingAthlete.setEmail(updatedAthlete.getEmail());
        }
        if (updatedAthlete.getSport() != null) {
            existingAthlete.setSport(updatedAthlete.getSport());
        }
        if (updatedAthlete.getAge() != 0) {
            existingAthlete.setAge(updatedAthlete.getAge());
        }
        if (updatedAthlete.getWeight() != 0.0) {
            existingAthlete.setWeight(updatedAthlete.getWeight());
        }
        if (updatedAthlete.getHeight() != 0.0) {
            existingAthlete.setHeight(updatedAthlete.getHeight());
        }
        if (updatedAthlete.getCoachId() != 0) {
            existingAthlete.setCoachId(updatedAthlete.getCoachId());
        }

        if (updatedAthlete.getGoalCalorie() != 0) {
            existingAthlete.setGoalCalorie(updatedAthlete.getGoalCalorie());
        }

        return athleteRepository.save(existingAthlete);
    }

    @Override
    public Athlete getAthleteById(int id) {
        return athleteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Athlete with ID " + id + " not found"));
    }

    @Override
    public Athlete getAthleteByEmail(String email) {
        return athleteRepository.findByEmail(email);
    }

    @Override
    public List<Athlete> getAthletesByCoachId(int coachId) {
        return athleteRepository.findByCoachId(coachId);
    }

    @Override
    public void deleteAthlete(int id) {
        Athlete athlete = athleteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Athlete with ID " + id + " not found"));
        athleteRepository.delete(athlete);
    }

    @Override
    public List<CoachDto> getAllCoaches() {
        return coachClient.getAllCoaches();
    }

    @Override
    public List<GoalDetailsDto> getAthleteGoals(int athleteId) {

        List<GoalDto> goals = goalClient.getGoalsByAthleteId(athleteId);
        List<TrainingSessionDto> trainingSessions = trainingSessionClient.getTrainingSessionsByAthleteId(athleteId);
        List<Integer> goalsToDelete = new ArrayList<>();

        Map<String, Map<String, GoalDetailsDto.ExerciseSummary>> categorizedExercises = new HashMap<>();

        for (GoalDto goal : goals) {
            String category = goal.getTargetCategory();
            String targetExerciseName = goal.getTargetExerciseName();
            double targetValue = goal.getTargetValue();

            GoalDetailsDto.ExerciseSummary exerciseSummary = new GoalDetailsDto.ExerciseSummary(0.0, targetValue);

            double cumulativeCurrentValue = trainingSessions.stream()
                    .flatMap(session -> session.getExercises().stream()) // Fetch exercises list
                    .filter(exercise -> exercise.getCategory().equals(category)
                            && exercise.getExerciseName().equals(targetExerciseName))
                    .mapToDouble(ExerciseDto::getCurrentValue) // Fetch current value
                    .sum();

            exerciseSummary.setCurrentValue(cumulativeCurrentValue);

            double progress = (cumulativeCurrentValue / targetValue) * 100;

            categorizedExercises.computeIfAbsent(category, k -> new HashMap<>())
                    .put(targetExerciseName, exerciseSummary);

            if (progress >= 100) {
                goalsToDelete.add(goal.getId());
            }
        }

        if (!goalsToDelete.isEmpty()) {
            goalClient.deleteGoals(goalsToDelete);
        }

        return categorizedExercises.entrySet().stream()
                .map(entry -> {
                    GoalDetailsDto goalDetails = new GoalDetailsDto();
                    goalDetails.setCategory(entry.getKey());
                    goalDetails.setCategorizedExercises(entry.getValue());
                    return goalDetails;
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean isRelated(int athleteId, int coachId) {
        Athlete athlete = athleteRepository.findById(athleteId).orElseThrow(()->new AthleteNotFoundException("Athlete with id : "+athleteId+" not found!!!"));
        return athlete.getCoachId() == coachId;
    }

    @Override
    public Coach getCoachByAthleteId(int id) {
        int coachId = athleteRepository.findById(id).get().getCoachId();
        return coachClient.getCoachById(coachId);
    }

//    @Override
//    public List<Athlete> findByCoachEmail(String coachEmail) {
//        return athleteRepository.findByCoachEmail(coachEmail);
//    }


}
