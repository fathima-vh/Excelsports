package com.ust.excelsports.api;

import com.ust.excelsports.dto.AthleteDto;
import com.ust.excelsports.dto.Coach;
import com.ust.excelsports.dto.CoachDto;
import com.ust.excelsports.dto.GoalDetailsDto;
import com.ust.excelsports.model.Athlete;
import com.ust.excelsports.service.AthleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/athletes")
public class AthleteController {

    @Autowired
    private  AthleteService athleteService;


    // Get a list of all athletes
//    @GetMapping
//    public List<Athlete> getAllAthletes() {
//        return athleteService.();
//    }

    // Get an athlete by ID
    @GetMapping("/id/{id}")
    public Athlete getAthleteById(@PathVariable int id) {
        return athleteService.getAthleteById(id);
    }

    @GetMapping("/{email}")
    public Athlete getAthleteByEmail(@PathVariable String email) {
        return athleteService.getAthleteByEmail(email);
    }

    // Create a new athlete
    @PostMapping
    public Athlete createAthlete(@RequestBody AthleteDto athlete) {
        return athleteService.createAthlete(athlete);
    }

    // Update an existing athlete
    @PutMapping("/{id}")
    public Athlete updateAthlete(@PathVariable int id, @RequestBody Athlete athlete) {
        return athleteService.updateAthlete(id, athlete);
    }

    // Delete an athlete by ID
    @DeleteMapping("/{id}")
    public void deleteAthlete(@PathVariable int id) {
        athleteService.deleteAthlete(id);
    }

    @GetMapping("/coaches")
    public List<CoachDto> getAllCoaches(){
        return athleteService.getAllCoaches();
    }

    @GetMapping("/coach/{athleteId}")
    public ResponseEntity<Coach> getCoachByAthleteId(@PathVariable int athleteId){

        Coach coach = athleteService.getCoachByAthleteId(athleteId);
        return ResponseEntity.ok().body(coach);
    }
    //get goals by athlete-id

    @GetMapping("/{athleteId}/goals")
    public ResponseEntity<List<GoalDetailsDto>> getAthleteGoals(@PathVariable int athleteId) {
        List<GoalDetailsDto> goals = athleteService.getAthleteGoals(athleteId);
        return ResponseEntity.ok(goals);
    }

    //to check is athlete and coach is related

    @GetMapping("/{athleteId}/coach/{coachId}/validate")
    public boolean isRelated(@PathVariable int athleteId,
                             @PathVariable int coachId){
        return athleteService.isRelated(athleteId,coachId);
    }

    @GetMapping("/coaches/{coachId}")
    public List<Athlete> getAthletesByCoachId(@PathVariable int coachId){
        return athleteService.getAthletesByCoachId(coachId);
    }


}
