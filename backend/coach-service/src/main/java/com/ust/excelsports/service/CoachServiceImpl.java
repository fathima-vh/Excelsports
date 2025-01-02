package com.ust.excelsports.service;

import com.ust.excelsports.client.AthleteClient;
import com.ust.excelsports.client.AuthClient;
import com.ust.excelsports.dto.*;
import com.ust.excelsports.exception.CoachNotFoundException;
import com.ust.excelsports.exception.DuplicateCoachException;
import com.ust.excelsports.model.Coach;
import com.ust.excelsports.model.RoleEnum;
import com.ust.excelsports.repository.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CoachServiceImpl implements CoachService{

   @Autowired
   private CoachRepository coachRepository;

   @Autowired
   private AthleteClient athleteClient;

   @Autowired
   private AuthClient authClient;

    public Coach saveCoach(CreateCoachDto createCoachDto) {

        if(coachRepository.findByEmail(createCoachDto.getEmail())!=null){
            throw new DuplicateCoachException("Email already exists!!");
        }
        Coach coach = new Coach();
        coach.setName(createCoachDto.getName());
        coach.setEmail(createCoachDto.getEmail());
        coach.setPassword(createCoachDto.getPassword());
        coach.setSport(createCoachDto.getSport());
        coach.setExperience(createCoachDto.getExperience());
        coach.setAthleteCount(0);
        coach.setRole(RoleEnum.COACH);

        //authClient.register(new UserModelDto(coach.getName(), coach.getEmail(), coach.getPassword(), coach.getRole()));

         return coachRepository.save(coach);
    }

    @Override
    public List<CoachDetailsDto> getAllCoaches() {
        List<Coach> coaches = coachRepository.findAll();
        return coaches.stream()
                .map(coach -> new CoachDetailsDto(
                        coach.getId(),
                        coach.getName(),
                        coach.getSport(),
                        coach.getExperience(),
                        coach.getEmail(),
                        coach.getAthleteCount()
                ))
                .collect(Collectors.toList());
    }


    @Override
    public Optional<Coach> getCoachById(int id) {
        if(!coachRepository.existsById(id)){
            throw new CoachNotFoundException("Coach with id : "+id+"  not found!!"); // Or throw an exception if coach not found
        }
        return coachRepository.findById(id);
    }

    @Override
    public void deleteCoach(int id) {
        if(!coachRepository.existsById(id)){
            throw new CoachNotFoundException("Coach with id : "+id+"  not found!!"); // Or throw an exception if coach not found
        }
        coachRepository.deleteById(id);
    }


    @Override
    public Coach getCoachWithAthletes(int coachId) {
        Optional<Coach> coachOptional = coachRepository.findById(coachId);

        if (coachOptional.isPresent()) {
            Coach coach = coachOptional.get();
            return coach;
        }

        throw new CoachNotFoundException("Coach with id : "+coachId+"  not found!!"); // Or throw an exception if coach not found
    }

    @Override
    public Coach updateCoach(int id,Coach coach) {

        if(coachRepository.existsById(id)){
            Coach updatedCoach= coachRepository.findById(id).get();
            if(coach.getName()!=null){
                updatedCoach.setName(coach.getName());
            }
            if(coach.getEmail()!=null){
                updatedCoach.setEmail(coach.getEmail());
            }
            if (coach.getPassword()!=null){
                updatedCoach.setPassword(coach.getPassword());
            }
            if(coach.getSport()!=null){
                updatedCoach.setSport(coach.getSport());
            }
            if(coach.getExperience()>0){
                updatedCoach.setExperience(coach.getExperience());
            }


            return coachRepository.save(updatedCoach);
        }
        throw new CoachNotFoundException("Coach with id : "+coach.getId()+"  not found!!");
    }

    @Override
    public Coach getCoachByEmail(String email) {
        return coachRepository.findByEmail(email);
    }

//    @Override
//    public List<Athlete> getCoachWithAthletesByEmail(String email) {
//        List<Athlete> athleteList =athleteClient.findByCoachEmail(email);
//        return athleteList;
//    }


}
