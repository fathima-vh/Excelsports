package com.ust.excelsports.service;

import com.ust.excelsports.model.Category;
import com.ust.excelsports.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(int id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public List<Category> getCategoryByName(String name) {
        return categoryRepository.findByCategoryName(name);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public List<String> getExercisesByCategoryName(String categoryName) {
        return categoryRepository.findExerciseNamesByCategoryName(categoryName);
    }

    @Override
    public List<String> getAllCategories() {
        return categoryRepository.findDistinctCategoryNames();
    }

    @Override
    public int getCalorie(String exerciseName) {
        return categoryRepository.findCalorieByExerciseName(exerciseName);
    }

    public Integer getCaloriesByExerciseName(String exerciseName) {
        List<Integer> calories = categoryRepository.findCaloriesByExerciseName(exerciseName);

        if (calories.isEmpty()) {
            throw new RuntimeException("Exercise not found");
        }

        // If you want just the first calorie value:
        return calories.get(0);
    }
}
