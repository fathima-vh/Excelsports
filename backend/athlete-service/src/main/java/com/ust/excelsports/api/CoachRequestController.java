package com.ust.excelsports.api;

import com.ust.excelsports.model.CoachRequest;
import com.ust.excelsports.repository.CoachRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/athletes")
public class CoachRequestController {

    @Autowired
    private CoachRequestRepository coachRequestRepository;

    @PostMapping("/{athleteId}/request-coach/{coachId}")
    public ResponseEntity<String> requestCoach(@PathVariable int athleteId,
                                               @PathVariable int coachId) {

        // Check if there is an existing PENDING request to the same coach
        Optional<CoachRequest> existingRequestForSameCoach = coachRequestRepository.findByAthleteIdAndCoachIdAndStatus(
                athleteId, coachId, CoachRequest.RequestStatus.PENDING);
        if (existingRequestForSameCoach.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("You already have a pending request with this coach. Please wait until it's accepted or rejected.");
        }

        // Check if there is a pending request to another coach
        Optional<CoachRequest> pendingRequestToAnotherCoach = coachRequestRepository.findByAthleteIdAndStatusAndCoachIdNot(
                athleteId, CoachRequest.RequestStatus.PENDING, coachId);
        if (pendingRequestToAnotherCoach.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("You have a pending request with another coach. Please wait until it's accepted or rejected.");
        }

        // Create the new coach request
        CoachRequest coachRequest = new CoachRequest();
        coachRequest.setAthleteId(athleteId);
        coachRequest.setCoachId(coachId);
        coachRequest.setStatus(CoachRequest.RequestStatus.PENDING);
        coachRequest.setRequestedAt(LocalDateTime.now());

        // Save the new coach request
        coachRequestRepository.save(coachRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body("Coach request sent successfully");
    }


    @DeleteMapping("/{athleteId}/delete-request/{coachId}")
    public ResponseEntity<String> deleteRequest(@PathVariable int athleteId,
                                                @PathVariable int coachId) {

        // Find the existing request
        Optional<CoachRequest> existingRequest = coachRequestRepository.findByAthleteIdAndCoachId(athleteId, coachId);
        if (!existingRequest.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found.");
        }

        // Ensure the request is still pending (can only delete pending requests)
        CoachRequest request = existingRequest.get();
        if (request.getStatus() != CoachRequest.RequestStatus.PENDING) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can only delete pending requests.");
        }

        // Delete the request
        coachRequestRepository.delete(request);

        return ResponseEntity.status(HttpStatus.OK).body("Coach request deleted successfully");
    }

}
