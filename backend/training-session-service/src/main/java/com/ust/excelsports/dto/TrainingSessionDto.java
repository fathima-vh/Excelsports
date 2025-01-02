package com.ust.excelsports.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TrainingSessionDto {
    private int id;
    private LocalDate date;
    private int duration;
    private int athleteId;
}
