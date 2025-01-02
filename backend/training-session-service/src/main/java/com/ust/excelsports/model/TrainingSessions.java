package com.ust.excelsports.model;

import com.ust.excelsports.dto.ExerciseDto;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class TrainingSessions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private LocalDate date;
    private double duration;

    private int athleteId;

    private int caloriesBurned;

    @Transient
    private List<ExerciseDto> exercises;
}
