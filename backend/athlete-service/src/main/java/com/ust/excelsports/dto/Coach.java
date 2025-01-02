package com.ust.excelsports.dto;

import com.ust.excelsports.model.RoleEnum;
import jakarta.persistence.*;
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

    @Enumerated(value = EnumType.STRING)
    private RoleEnum role;


}
