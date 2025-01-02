package com.ust.excelsports.repository;

import com.ust.excelsports.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    List<Category> findByCategoryName(String name);
    @Query("SELECT c.exerciseName FROM Category c WHERE c.categoryName = :categoryName")
    List<String> findExerciseNamesByCategoryName(String categoryName);

    @Query("SELECT DISTINCT c.categoryName FROM Category c")
    List<String> findDistinctCategoryNames();

    @Query("SELECT c.calorie FROM Category c WHERE c.exerciseName = :exerciseName")
    int findCalorieByExerciseName(String exerciseName);

    @Query("SELECT c.calorie FROM Category c WHERE c.exerciseName = :exerciseName")
    List<Integer> findCaloriesByExerciseName(String exerciseName);
}
