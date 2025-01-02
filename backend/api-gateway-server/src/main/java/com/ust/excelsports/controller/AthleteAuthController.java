package com.ust.excelsports.controller;

import com.ust.excelsports.dto.AthleteDto;
import com.ust.excelsports.dto.CreateCoachDto;
import com.ust.excelsports.dto.JwtToken;
import com.ust.excelsports.exception.AthleteNotFoundException;
import com.ust.excelsports.exception.InvalidCredentialsException;
import com.ust.excelsports.model.Athlete;
import com.ust.excelsports.model.Coach;
import com.ust.excelsports.model.RoleEnum;
import com.ust.excelsports.model.User;
import com.ust.excelsports.service.JwtService;
import com.ust.excelsports.service.UserService;
import com.ust.excelsports.userrepo.AthleteRepository;
import com.ust.excelsports.userrepo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/athlete")
public class AthleteAuthController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService service;

    @Autowired
    private AthleteRepository athleteRepository;


    @PostMapping("/register")
    public Athlete register(@RequestBody AthleteDto user){
        return service.saveAthlete(user);
    }


    @PostMapping("/login")
    public JwtToken login(@RequestBody User loginRequest) {
        Athlete user = athleteRepository.findByEmail(loginRequest.getEmail());
        if (loginRequest.getRole() == RoleEnum.ATHLETE && user!=null) {

            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
                return new JwtToken(jwtService.generateToken(loginRequest.getEmail()), loginRequest.getEmail()); // Replace with JWT token if required
            } catch (AuthenticationException e) {
                throw new InvalidCredentialsException("Invalid credentials for athlete login");
            }
        }
        throw new AthleteNotFoundException("Email does not belong to athlete");

    }
}
