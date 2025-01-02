package com.ust.excelsports.controller;

import com.ust.excelsports.dto.AthleteDto;
import com.ust.excelsports.dto.CreateCoachDto;
import com.ust.excelsports.dto.JwtToken;
import com.ust.excelsports.exception.CoachNotFoundException;
import com.ust.excelsports.exception.InvalidCredentialsException;
import com.ust.excelsports.model.Athlete;
import com.ust.excelsports.model.Coach;
import com.ust.excelsports.model.RoleEnum;
import com.ust.excelsports.model.User;
import com.ust.excelsports.service.JwtService;
import com.ust.excelsports.service.UserService;
import com.ust.excelsports.userrepo.CoachRepository;
import com.ust.excelsports.userrepo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/coach")
public class CoachAuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService service;

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/register")
    public Coach register(@RequestBody CreateCoachDto user){
        return service.saveCoach(user);
    }


    @PostMapping("/login")
    public JwtToken login(@RequestBody User loginRequest) {

        Coach user = coachRepository.findByEmail(loginRequest.getEmail());
        if (loginRequest.getRole() == RoleEnum.COACH && user!=null) {

            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
                return new JwtToken(jwtService.generateToken(loginRequest.getEmail()), loginRequest.getEmail());
            } catch (AuthenticationException e) {
                throw new InvalidCredentialsException("Invalid credentials for coach login");
            }

        }
        //return new JwtToken("Email does not belong to coach!!!");
        throw new CoachNotFoundException("Email does not belong to coach!!!");

    }


}
