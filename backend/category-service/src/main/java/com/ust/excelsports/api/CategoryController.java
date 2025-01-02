package com.ust.excelsports.api;

import com.ust.excelsports.model.Category;
import com.ust.excelsports.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Create a new category
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.createCategory(category);
        return ResponseEntity.ok(createdCategory);
    }

    // Get a category by ID
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable int id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    // Get a category by name
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Category>> getCategoryByName(@PathVariable String name) {
        List<Category> category = categoryService.getCategoryByName(name);
        return ResponseEntity.ok(category);
    }

    // Get all categories
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategory();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<String>> getExercisesByCategoryName(@PathVariable String categoryName){
        return ResponseEntity.ok().body(categoryService.getExercisesByCategoryName(categoryName));
    }

    @GetMapping("/category-names")
    public ResponseEntity<List<String>> getAllCategoryNames(){
        return ResponseEntity.ok().body(categoryService.getAllCategories());
    }

    @GetMapping("/exercise/{exercise}/calorie")
    public ResponseEntity<Integer> getCalorie(
            @PathVariable String exercise
    ){
        return ResponseEntity.ok(categoryService.getCalorie(exercise));
    }

    @GetMapping("/calories/{exerciseName}")
    public Integer getCalories(@PathVariable String exerciseName) {
        return categoryService.getCaloriesByExerciseName(exerciseName);
    }
}
