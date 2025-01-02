package com.ust.excelsports.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoachRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int athleteId;
    private int coachId;
    
    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    private LocalDateTime requestedAt;


    public enum RequestStatus {
            PENDING,
            ACCEPTED,
            DECLINED
    }
}
