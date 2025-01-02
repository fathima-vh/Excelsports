package com.ust.excelsports.repository;

import com.ust.excelsports.model.CoachRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoachRequestRepository extends JpaRepository<CoachRequest, Long> {
    List<CoachRequest> findByCoachIdAndStatus(int coachId, CoachRequest.RequestStatus status);
    List<CoachRequest> findByAthleteIdAndStatus(int athleteId, CoachRequest.RequestStatus status);

    Optional<CoachRequest> findByAthleteIdAndStatusAndCoachIdNot(int athleteId, CoachRequest.RequestStatus requestStatus, int coachId);

    Optional<CoachRequest> findByAthleteIdAndCoachId(int athleteId, int coachId);

    Optional<CoachRequest> findByAthleteIdAndCoachIdAndStatus(int athleteId, int coachId, CoachRequest.RequestStatus requestStatus);
}
