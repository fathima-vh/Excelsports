package com.ust.excelsports.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int athleteId;
    private String targetCategory;
    private String targetExerciseName;
    private int targetValue;

}
