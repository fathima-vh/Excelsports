package com.ust.excelsports.service;

import com.ust.excelsports.model.Coach;
import com.ust.excelsports.model.RoleEnum;
import com.ust.excelsports.model.User;
import com.ust.excelsports.model.UserPrincipal;
import com.ust.excelsports.userrepo.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CoachUserDetailsService implements UserDetailsService {

    @Autowired
    private CoachRepository coachRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Coach coach = coachRepo.findByEmail(username);
        User user = new User(coach.getEmail(),coach.getPassword(), RoleEnum.COACH);
        if (coach == null) {
            throw new UsernameNotFoundException("Coach not found with email: " + username);
        }
        return new UserPrincipal(user); // Assuming UserPrincipal can handle Coach objects
    }
}
