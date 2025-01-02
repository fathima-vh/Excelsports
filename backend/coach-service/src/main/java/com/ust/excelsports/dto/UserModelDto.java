package com.ust.excelsports.dto;

import com.ust.excelsports.model.RoleEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModelDto {

    private String username;
    private String email;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private RoleEnum role;


    /*public UserModelDto converter(CreateCoachDto coach){
        return new UserModelDto(coach.getName(), coach.getEmail(), coach.getPassword(),coach.getRole());
    }*/

}
