package com.ust.excelsports.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class CalorieHistoryDTO {
    // Getters and setters
    private String date;
    private Long totalCalories;

    public CalorieHistoryDTO(String date, Long totalCalories) {
        this.date = date;
        this.totalCalories = totalCalories;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTotalCalories(Long totalCalories) {
        this.totalCalories = totalCalories;
    }
}
