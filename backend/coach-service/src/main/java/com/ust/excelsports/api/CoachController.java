package com.ust.excelsports.api;

import com.ust.excelsports.client.AthleteClient;
import com.ust.excelsports.client.TrainingSessionClient;
import com.ust.excelsports.dto.Athlete;
import com.ust.excelsports.dto.CoachDetailsDto;
import com.ust.excelsports.dto.CreateCoachDto;
import com.ust.excelsports.dto.TrainingSessionDto;
import com.ust.excelsports.model.Coach;
import com.ust.excelsports.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/coaches")
public class CoachController {

    @Autowired
    private CoachService coachService;

    @Autowired
    private AthleteClient athleteClient;

    @Autowired
    private TrainingSessionClient trainingSessionClient;
    @GetMapping("/athletes/{coachId}")
    public ResponseEntity<List<Athlete>> getAthletesByCoachId(@PathVariable int coachId){
        List<Athlete> athleteList=athleteClient.getAthletesByCoachId(coachId);
        return ResponseEntity.ok(athleteList);
    }

    // Create a new coach
    @PostMapping
    public ResponseEntity<Coach> createCoach(@RequestBody CreateCoachDto createCoachDto) {
        Coach savedCoach = coachService.saveCoach(createCoachDto);
        return ResponseEntity.ok(savedCoach);
    }

    // Get all coaches
    @GetMapping
    public ResponseEntity<List<CoachDetailsDto>> getAllCoaches() {
        List<CoachDetailsDto> coaches = coachService.getAllCoaches();
        return ResponseEntity.ok(coaches);
    }

    // Get coach by ID
    @GetMapping("/id/{id}")
    public ResponseEntity<Coach> getCoachById(@PathVariable int id) {
        Optional<Coach> coach = coachService.getCoachById(id);
        return coach.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{email}")
    public ResponseEntity<Coach> getCoachByEmail(@PathVariable String email){
        return ResponseEntity.ok().body(coachService.getCoachByEmail(email));
    }

    // Delete coach by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoach(@PathVariable int id) {
        coachService.deleteCoach(id);
        return ResponseEntity.noContent().build();
    }

    // Get coach with athletes
    @GetMapping("/{coachId}/athletes")
    public ResponseEntity<Coach> getCoachWithAthletes(@PathVariable int coachId) {
        Coach coach = coachService.getCoachWithAthletes(coachId);
        return ResponseEntity.ok(coach);
    }

//    // Get coach with athletes
//    @GetMapping("/{coachEmail}/athletes")
//    public ResponseEntity<List<Athlete>> getCoachWithAthletesByEmail(@PathVariable String email) {
//        List<Athlete> athleteList= coachService.getCoachWithAthletesByEmail(email);
//        return ResponseEntity.ok(athleteList);
//    }

    // Update coach details
    @PutMapping("/{id}")
    public ResponseEntity<Coach> updateCoach(@PathVariable int id, @RequestBody Coach coach) {
        Coach updatedCoach = coachService.updateCoach(id, coach);
        return ResponseEntity.ok(updatedCoach);
    }
}
