package com.platform.backend.goals.dto;

import com.platform.backend.goals.domain.GoalDifficulty;
import com.platform.backend.goals.domain.GoalVisibility;

public class UpdateGoalRequest {

    private GoalDifficulty difficulty;
    private Integer dailyMinimumEffort;
    private GoalVisibility visibility;

    public GoalDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(GoalDifficulty difficulty) {
        this.difficulty = difficulty;
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
