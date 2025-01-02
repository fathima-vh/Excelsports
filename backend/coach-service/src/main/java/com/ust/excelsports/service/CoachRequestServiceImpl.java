package com.ust.excelsports.service;

import com.ust.excelsports.client.AthleteClient;
import com.ust.excelsports.dto.Athlete;
import com.ust.excelsports.dto.CoachRequestsDto;
import com.ust.excelsports.model.Coach;
import com.ust.excelsports.model.CoachRequest;
import com.ust.excelsports.repository.CoachRepository;
import com.ust.excelsports.repository.CoachRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
@Service
public class CoachRequestServiceImpl {

    @Autowired
    private CoachRequestRepository coachRequestRepository;

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private AthleteClient athleteClient;

    public ResponseEntity<String> requestHandler(int coachId, Long requestId, String response) {

        // Fetch the coach request
        Optional<CoachRequest> requestOpt = coachRequestRepository.findById(requestId);

        if (requestOpt.isEmpty()) {
            return new ResponseEntity<>("Request not found", HttpStatus.NOT_FOUND);
        }

        Coach coach = coachRepository.findById(coachId)
                .orElseThrow(() -> new RuntimeException("Coach not found"));

        // Fetch the coach request
        CoachRequest request = requestOpt.get();

        // Validate coach ID in the request
        if (request.getCoachId() != coachId) {
            return new ResponseEntity<>("You cannot respond to this request", HttpStatus.FORBIDDEN);
        }

        // Handle request based on response
        try {
            if ("ACCEPT".equalsIgnoreCase(response)) {
                coach.setAthleteCount(coach.getAthleteCount() + 1);
                int athleteId = request.getAthleteId();
                Athlete athlete = athleteClient.getAthleteById(athleteId);

                if (athlete == null) {
                    return new ResponseEntity<>("Athlete not found", HttpStatus.NOT_FOUND);
                }

                athlete.setCoachId(coachId);
                athleteClient.updateAthlete(athleteId, athlete);

                request.setStatus(CoachRequest.RequestStatus.ACCEPTED);
            } else if ("DECLINE".equalsIgnoreCase(response)) {
                request.setStatus(CoachRequest.RequestStatus.DECLINED);
            } else {
                return new ResponseEntity<>("Invalid response", HttpStatus.BAD_REQUEST);
            }

            coachRequestRepository.save(request);
            return new ResponseEntity<>("Request " + response + "ed successfully", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public int getPendingRequestCount(int coachId){
        return coachRequestRepository.countPendingRequestsByCoachId(coachId);
    }

}
