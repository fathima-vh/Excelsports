package com.ust.excelsports.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
@Entity
public class User {

    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private RoleEnum role;

    public User (String email,String password,RoleEnum role){
        this.email=email;
        this.password=password;
        this.role=role;
    }

}
