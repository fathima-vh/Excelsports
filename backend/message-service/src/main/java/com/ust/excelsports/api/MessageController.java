package com.ust.excelsports.api;

import com.ust.excelsports.client.AthleteClient;
import com.ust.excelsports.client.CoachClient;
import com.ust.excelsports.dto.Athlete;
import com.ust.excelsports.dto.Coach;
import com.ust.excelsports.dto.MessageDto;
import com.ust.excelsports.model.Message;
import com.ust.excelsports.model.MessageSender;
import com.ust.excelsports.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private AthleteClient athleteClient;

    @Autowired
    private CoachClient coachClient;

    @PostMapping("/send")
    public ResponseEntity<MessageDto> sendMessage(@RequestBody Message messageRequest) {
        // Call service to save the message
        Message savedMessage = messageService.sendMessage(
                messageRequest.getAthleteId(),
                messageRequest.getCoachId(),
                messageRequest.getRole(),
                messageRequest.getContent()
        );

        // If the message is successfully saved, return the saved message in the response
        if (savedMessage != null) {
            // Create MessageDto from the saved message
            Athlete athlete = athleteClient.getAthleteById(savedMessage.getAthleteId());
            Coach coach =coachClient.getCoachById(savedMessage.getCoachId());
            MessageDto messageDto = new MessageDto(
                    savedMessage.getId(),
                    savedMessage.getAthleteId(),
                    savedMessage.getCoachId(),
                    athlete != null ? athlete.getName() : "Unknown Athlete",
                    coach!=null? coach.getName():" Unknown coach",
                    savedMessage.getRole(), // Ensure the role is correctly set
                    savedMessage.getContent(),
                    savedMessage.getSentAt(),
                    savedMessage.isRead()
            );

            return ResponseEntity.ok(messageDto);
        } else {
            // Handle case where message saving fails
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/coach-inbox")
    public List<MessageDto> getMessagesOfCoach(@RequestParam int coachId) {
        List<Message> msg= messageService.getMessagesForCoach(coachId);
        List<MessageDto> msgDto = msg.stream().map(
                i->{
                    Athlete athlete = athleteClient.getAthleteById(i.getAthleteId());
                    Coach coach =coachClient.getCoachById(i.getCoachId());
                    return new MessageDto(
                            i.getId(),
                            i.getAthleteId(),
                            i.getCoachId(),
                            athlete != null ? athlete.getName() : "Unknown Athlete",
                            coach!=null? coach.getName():" Unknown coach",
                            i.getRole(),
                            i.getContent(),
                            i.getSentAt(),
                            i.isRead()
                    );
                }
        ).collect(Collectors.toList());
        return msgDto;
    }

    @GetMapping("/athlete-inbox")
    public List<MessageDto> getMessagesOfCoachAndAthlete(@RequestParam int athleteId,
                                         @RequestParam int coachId) {
         List<Message> msg= messageService.viewMessages(athleteId,coachId);
         List<MessageDto> msgDto = msg.stream().map(
                 i->{
                     Athlete athlete = athleteClient.getAthleteById(i.getAthleteId());
                     Coach coach =coachClient.getCoachById(i.getCoachId());

                     return new MessageDto(
                             i.getId(),
                             i.getAthleteId(),
                             i.getCoachId(),
                             athlete != null ? athlete.getName() : "Unknown Athlete",
                             coach != null? coach.getName() : "Unknown coach",
                             i.getRole(),
                             i.getContent(),
                             i.getSentAt(),
                             i.isRead()
                     );
                 }
         ).collect(Collectors.toList());
        return msgDto;
    }

    @GetMapping("/athletes/{coachId}")
    public ResponseEntity<List<Athlete>> getAthletesByCoachId(@PathVariable int coachId){
        List<Athlete> athleteList=athleteClient.getAthletesByCoachId(coachId);
        return ResponseEntity.ok(athleteList);
    }

    @PutMapping("/mark-as-read/{messageId}")
    public ResponseEntity<Void> markAsRead(@PathVariable Long messageId) {
        messageService.markMessageAsRead(messageId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long messageId) {
        messageService.deleteById(messageId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/unread-count")
    public ResponseEntity<Integer> getUnreadMessageCount(@PathVariable int id) {
        int count = messageService.getUnreadMessageCountForCoach(id);
        System.out.println("count : "+count);
        return ResponseEntity.ok(count);
    }
}
