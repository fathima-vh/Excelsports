package com.ust.excelsports.dto;

import com.ust.excelsports.model.CoachRequest;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoachRequestsDto {

    private long requestId;
    private int coachId;
    private int athleteId;

//    private String name;  // Coach's name
//    private String sport; // Coach's sport
    private String athleteName;  // Athlete's name
    private String athleteSport; // Athlete's sport

    @Enumerated(EnumType.STRING)
    private RequestStatus status;
//private CoachRequest.RequestStatus status;
    private LocalDateTime requestedAt;

    public enum RequestStatus {
        PENDING,
        ACCEPTED,
        DECLINED
    }
}
