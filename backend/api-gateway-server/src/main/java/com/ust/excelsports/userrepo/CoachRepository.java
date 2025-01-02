package com.ust.excelsports.userrepo;

import com.ust.excelsports.model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoachRepository extends JpaRepository<Coach,Integer> {

    Coach findByEmail(String email);
    boolean existsByEmail(String email);

}
