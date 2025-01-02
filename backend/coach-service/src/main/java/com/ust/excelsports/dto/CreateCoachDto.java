package com.ust.excelsports.dto;

import com.ust.excelsports.model.RoleEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class CreateCoachDto {
    private String name;
    private String email;
    private String password;
    private String sport;
    private int experience;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;
}
