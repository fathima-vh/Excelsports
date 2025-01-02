package com.ust.excelsports.service;

import com.ust.excelsports.client.AthleteClient;
import com.ust.excelsports.client.CoachClient;
import com.ust.excelsports.dto.AthleteDto;
import com.ust.excelsports.dto.CreateCoachDto;
import com.ust.excelsports.exception.DuplicateAthleteException;
import com.ust.excelsports.exception.DuplicateCoachException;
import com.ust.excelsports.model.Athlete;
import com.ust.excelsports.model.Coach;
import com.ust.excelsports.model.RoleEnum;
import com.ust.excelsports.model.User;
import com.ust.excelsports.userrepo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private CoachClient coachClient;

    @Autowired
    private AthleteClient athleteClient;

    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    public User saveUser(User user){

        return repo.save(user);
    }

    public Coach saveCoach(CreateCoachDto coach){
        if (repo.findByEmail(coach.getEmail())!=null){
            throw new DuplicateCoachException("Coach already there!!");
        }
        coach.setPassword(encoder.encode(coach.getPassword()));
        System.out.println(coach.getPassword());
        saveUser(new User(coach.getEmail(), coach.getPassword(), RoleEnum.COACH));
        return coachClient.createCoach(coach);
    }

    public Athlete saveAthlete(AthleteDto athlete){
        if(repo.findByEmail(athlete.getEmail())!=null){
            throw new DuplicateAthleteException("Athlete already present");
        }
        athlete.setPassword(encoder.encode(athlete.getPassword()));
        System.out.println(athlete.getPassword());
        saveUser(new User(athlete.getEmail(), athlete.getPassword(), RoleEnum.ATHLETE));
        return athleteClient.createAthlete(athlete);
    }
}
