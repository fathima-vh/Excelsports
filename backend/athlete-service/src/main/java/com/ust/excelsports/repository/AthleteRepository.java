package com.ust.excelsports.repository;

import com.ust.excelsports.model.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AthleteRepository extends JpaRepository<Athlete, Integer> {

    // Find athlete by email (for login or lookup)
    Athlete findByEmail(String email);

    // Additional method to find athletes by coachId (to list athletes under a coach)
    List<Athlete> findByCoachId(int coachId);
//    List<Athlete> findByCoachEmail(String coachEmail);
}
