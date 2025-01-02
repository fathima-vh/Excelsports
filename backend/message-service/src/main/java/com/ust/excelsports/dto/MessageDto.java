package com.ust.excelsports.dto;

import com.ust.excelsports.model.MessageSender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

    private Long id;

    private int athleteId;
    private int coachId;
    private String athleteName;
    private String coachName;

    @Enumerated(value = EnumType.STRING)
    private MessageSender role;

    private String content;
    private LocalDateTime sentAt = LocalDateTime.now();

    private boolean read = false;
}
