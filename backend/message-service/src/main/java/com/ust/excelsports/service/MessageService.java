package com.ust.excelsports.service;

import com.ust.excelsports.client.AthleteClient;
import com.ust.excelsports.dto.MessageDto;
import com.ust.excelsports.model.Message;
import com.ust.excelsports.model.MessageSender;
import com.ust.excelsports.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AthleteClient athleteClient;

    public Message sendMessage(int athleteId, int coachId, MessageSender role, String content) {
        if (athleteClient.isRelated(athleteId, coachId)){
            Message message = new Message();
            message.setAthleteId(athleteId);
            message.setRole(role);
            message.setContent(content);
            message.setSentAt(LocalDateTime.now());
            message.setRead(false);
            message.setCoachId(coachId);

            return messageRepository.save(message);

        }
        throw new RuntimeException("Message didnt send");
    }

    public List<Message> getMessagesForCoach(int coachId) {
        return messageRepository.findByCoachId(coachId);
    }

    public List<Message> viewMessages(int athleteId, int coachId) {
        if (athleteClient.isRelated(athleteId, coachId)) {
            return messageRepository.findMessagesByAthleteIdOrCoachId(athleteId,coachId);

        }

        throw new RuntimeException("No Messages");
    }

    public void markMessageAsRead(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        message.setRead(true);
        messageRepository.save(message);
    }

    public void deleteById(Long messageId) {
        messageRepository.deleteById(messageId);
    }

    public int getUnreadMessageCountForCoach(int coachId) {
        return messageRepository.countUnreadMessagesByCoachId(coachId);
    }
}
