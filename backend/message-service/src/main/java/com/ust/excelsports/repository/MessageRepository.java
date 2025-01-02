package com.ust.excelsports.repository;


import com.ust.excelsports.dto.MessageDto;
import com.ust.excelsports.model.Message;
import com.ust.excelsports.model.MessageSender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findMessagesByAthleteIdAndCoachIdAndRole(int athleteId, int coachId, MessageSender role);
    List<Message> findByCoachId(int coachId);
    void deleteById(int athleteId);
    List<Message> findMessagesByAthleteIdOrCoachId(int athleteId, int coachId);


    @Query("SELECT COUNT(m) FROM Message m WHERE m.coachId = :coachId AND m.read = false AND m.role = 'ATHLETE'")
    int countUnreadMessagesByCoachId(@Param("coachId") int coachId);
}
