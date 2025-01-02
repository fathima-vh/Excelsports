package com.ust.excelsports.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int athleteId;
    private int coachId;

    @Enumerated(value = EnumType.STRING)
    private MessageSender role;

    private String content;
    private LocalDateTime sentAt = LocalDateTime.now();

    private boolean read = false;
}
