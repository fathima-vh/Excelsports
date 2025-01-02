package com.ust.excelsports.service;

import com.ust.excelsports.model.Category;

import java.util.List;

public interface CategoryService {

    public Category createCategory(Category category);
    public Category getCategoryById(int id);
    public List<Category> getCategoryByName(String name);
    public List<Category> getAllCategory();
    public  List<String> getExercisesByCategoryName(String categoryName);
    public  List<String> getAllCategories();
    public int getCalorie(String exerciseName);


    Integer getCaloriesByExerciseName(String exerciseName);
}
