package com.ust.excelsports.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Athlete {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private int id;

   private String name;
   private String email;
   private String password;
   private LocalDate dob;
   private String sport;
   private int age;
   private double weight;
   private double height;
   private int goalCalorie;

   @Enumerated(value = EnumType.STRING)
   private RoleEnum role;

   private int coachId;


}
