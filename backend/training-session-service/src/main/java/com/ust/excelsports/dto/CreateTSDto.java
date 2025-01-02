package com.ust.excelsports.dto;

import com.ust.excelsports.model.TrainingSessions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTSDto {

    private LocalDate date;
    private double duration;
    private int athleteId;

    public TrainingSessions convertToTS(TrainingSessions trainingSessions){
        trainingSessions.setDuration(this.getDuration());
        trainingSessions.setDate(this.getDate());
        trainingSessions.setAthleteId(this.getAthleteId());
        return  trainingSessions;
    }
}
