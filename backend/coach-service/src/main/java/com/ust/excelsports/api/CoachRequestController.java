package com.ust.excelsports.api;

import com.ust.excelsports.client.AthleteClient;
import com.ust.excelsports.dto.Athlete;
import com.ust.excelsports.dto.CoachRequestsDto;
import com.ust.excelsports.model.CoachRequest;
import com.ust.excelsports.repository.CoachRequestRepository;
import com.ust.excelsports.service.CoachRequestServiceImpl;
import jakarta.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/coaches")
public class CoachRequestController {

    @Autowired
    private CoachRequestRepository coachRequestRepository;

    @Autowired
    private CoachRequestServiceImpl requestService;

    @Autowired
    private AthleteClient athleteClient;

    @GetMapping("/{coachId}/requests1")
    public ResponseEntity<List<CoachRequest>> viewRequests1(@PathVariable int coachId) {

        List<CoachRequest> requests = coachRequestRepository.findByCoachIdAndStatus(coachId, CoachRequest.RequestStatus.PENDING);
        return ResponseEntity.ok(requests);
    }


    @GetMapping("/{coachId}/requests")
    public ResponseEntity<List<CoachRequestsDto>> viewRequests(@PathVariable int coachId) {
        // Fetch pending requests by coachId
        List<CoachRequest> coachRequests = coachRequestRepository.findByCoachIdAndStatus(coachId, CoachRequest.RequestStatus.PENDING);

        // Map the requests to CoachRequestDto including athlete's name and sport
        List<CoachRequestsDto> requestsDtoList = coachRequests.stream()
                .map(request -> {
                    // Fetch the athlete details using athleteId
                    Athlete athlete = athleteClient.getAthleteById(request.getAthleteId());
                    System.out.println("check rid : "+request.getId());

                    // Create and return the DTO with athlete and request details
                    return new CoachRequestsDto(
                            request.getId(),
                            request.getCoachId(),
                            request.getAthleteId(),
                            athlete != null ? athlete.getName() : "Unknown Athlete",  // Fallback if athlete not found
                            athlete != null ? athlete.getSport() : "Unknown Sport", // Fallback if athlete not found
                            CoachRequestsDto.RequestStatus.valueOf(request.getStatus().name()), // Map enum
                            request.getRequestedAt()
                    );
                })
                .collect(Collectors.toList());


        return ResponseEntity.ok(requestsDtoList);
    }
    @PostMapping("/{coachId}/requests/{requestId}")
    public ResponseEntity<String> respondToRequest(@PathVariable int coachId,
                                                   @PathVariable Long requestId,
                                                   @RequestParam("response") String response) {
        // Call service method to handle the response
        return requestService.requestHandler(coachId, requestId, response);
    }
    @DeleteMapping("/delete-request/{requestId}")
    public ResponseEntity<String> deleteRequestByRequestId(@PathVariable int requestId) {
        // Find the existing request by requestId
        Optional<CoachRequest> existingRequest = coachRequestRepository.findById((long) requestId);
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

    @GetMapping("{id}/no-of-pending-requests")
    ResponseEntity<Integer> getNumberOfPendingRequests(
            @PathVariable int id
    ){
        return ResponseEntity.ok().body(requestService.getPendingRequestCount(id));
    }

}
