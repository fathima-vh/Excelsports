package com.ust.excelsports.repository;

import com.ust.excelsports.dto.CoachRequestsDto;
import com.ust.excelsports.model.CoachRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface CoachRequestRepository extends JpaRepository<CoachRequest, Long> {

    List<CoachRequest> findByCoachIdAndStatus(int coachId, CoachRequest.RequestStatus status);

    List<CoachRequestsDto> findByCoachIdAndStatus(int coachId, CoachRequestsDto.RequestStatus requestStatus);

    @Query("SELECT COUNT(cr) FROM CoachRequest cr WHERE cr.coachId = :coachId AND cr.status = 'PENDING'")
    int countPendingRequestsByCoachId(@Param("coachId") int coachId);

}
