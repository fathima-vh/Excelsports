package com.ust.excelsports.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingSessionDto {

    private int id;
    private String date;
    private int duration;
    private List<ExerciseDto> exercises;
}
