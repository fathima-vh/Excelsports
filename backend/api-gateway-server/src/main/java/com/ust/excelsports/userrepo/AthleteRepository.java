package com.ust.excelsports.userrepo;

import com.ust.excelsports.model.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AthleteRepository extends JpaRepository<Athlete, Integer> {
    Athlete findByEmail(String email);
    boolean existsByEmail(String email);

}
