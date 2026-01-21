package com.platform.backend.goals.dto;

import java.time.LocalDate;

import com.platform.backend.goals.domain.GoalCategory;
import com.platform.backend.goals.domain.GoalDifficulty;
import com.platform.backend.goals.domain.GoalVisibility;

public class CreateGoalRequest {

    private GoalCategory category;
    private GoalDifficulty difficulty;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer dailyMinimumEffort;
    private GoalVisibility visibility;

    public GoalCategory getCategory() {
        return category;
    }

    public void setCategory(GoalCategory category) {
        this.category = category;
    }

    public GoalDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(GoalDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getDailyMinimumEffort() {
        return dailyMinimumEffort;
    }

    public void setDailyMinimumEffort(Integer dailyMinimumEffort) {
        this.dailyMinimumEffort = dailyMinimumEffort;
    }

    public GoalVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(GoalVisibility visibility) {
        this.visibility = visibility;
    }
}
