package com.ust.excelsports.dto;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TrainingSessions {

   
    private int id;

    private LocalDate date;
    private double duration;

    private int athleteId;

    private int caloriesBurned;
    
    private List<ExerciseDto> exercises;
}
