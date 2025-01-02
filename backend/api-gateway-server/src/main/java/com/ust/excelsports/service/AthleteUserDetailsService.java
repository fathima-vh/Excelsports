package com.ust.excelsports.service;

import com.ust.excelsports.model.Athlete;
import com.ust.excelsports.model.RoleEnum;
import com.ust.excelsports.model.User;
import com.ust.excelsports.model.UserPrincipal;
import com.ust.excelsports.userrepo.AthleteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AthleteUserDetailsService implements UserDetailsService {

    @Autowired
    private AthleteRepository athleteRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Athlete athlete = athleteRepo.findByEmail(username);
        User user = new User(athlete.getEmail(),athlete.getPassword(), RoleEnum.ATHLETE);
        if (athlete == null) {
            throw new UsernameNotFoundException("Athlete not found with username: " + username);
        }
        return new UserPrincipal(user); // Assuming UserPrincipal can handle Athlete objects
    }
}
