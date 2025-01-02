package com.ust.excelsports.dto;

import com.ust.excelsports.model.RoleEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AthleteDto {
    private String name;
    private String email;
    private String password;
    private LocalDate dob;
    private String sport;
    private int age;
    private double weight;
    private double height;

    @Enumerated(value = EnumType.STRING)
    private RoleEnum role;
}
