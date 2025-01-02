package com.ust.excelsports.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
