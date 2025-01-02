package com.ust.excelsports.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class Coach {


    private int id;
    private String name;
    private String email;
    private String password;
    private String sport;
    private int experience;
    private int athleteCount;

//    @Enumerated(value = EnumType.STRING)
//    private RoleEnum role;
}