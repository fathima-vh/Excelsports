package com.ust.excelsports.repository;

import com.ust.excelsports.dto.GetExerciseDto;
import com.ust.excelsports.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise,Integer> {

    Exercise findByExerciseName(String name);

    List<Exercise> findByTrainingSessionsId(int id);

//    @Query(value = "SELECT new com.ust.excelsports.dto.GetExerciseDto(e.exerciseName, e.currentValue, e.category) " +
//            "FROM Exercise e " +
//            "WHERE e.trainingSessionsId = :trainingSessionId")
//    List<GetExerciseDto> findExercisesByTrainingSessionId(@Param("trainingSessionId") int trainingSessionId);

}
