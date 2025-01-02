package com.ust.excelsports.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Athlete {

   private int id;
   private String name;
   private String email;
   private String password;
   private LocalDate dob;
   private String sport;
   private int age;
   private double weight;
   private double height;

   private int coachId;


}
