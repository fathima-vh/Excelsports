package com.ust.excelsports.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoachDto {

    private int id;
    private String name;
    private String sport;
    private int experience;
    private String email;
    private int athleteCount;

}
