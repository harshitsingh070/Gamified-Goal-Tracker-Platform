package com.platform.backend.goals.service;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.platform.backend.goals.domain.GoalStatus;
import com.platform.backend.goals.domain.GoalVisibility;
import com.platform.backend.goals.dto.CreateGoalRequest;
import com.platform.backend.goals.dto.UpdateGoalRequest;
import com.platform.backend.goals.model.Goal;
import com.platform.backend.goals.repository.GoalRepository;

@Service
public class GoalService {

    private final GoalRepository goalRepository;

    public GoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    // =========================
    // STEP 4 — CREATE GOAL
    // =========================
    public Goal createGoal(Long userId, CreateGoalRequest request) {

        if (request.getDailyMinimumEffort() <= 0) {
            throw new RuntimeException("Daily minimum effort must be greater than zero");
        }

        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new RuntimeException("Start date cannot be after end date");
        }

        Goal goal = new Goal();

        // 🔐 Ownership
        goal.setUserId(userId);

        // 📌 Immutable core fields
        goal.setCategory(request.getCategory());
        goal.setDifficulty(request.getDifficulty());
        goal.setStartDate(request.getStartDate());
        goal.setEndDate(request.getEndDate());
        goal.setDailyMinimumEffort(request.getDailyMinimumEffort());

        // 👁 Visibility
        goal.setVisibility(
                request.getVisibility() != null
                        ? request.getVisibility()
                        : GoalVisibility.PRIVATE
        );

        // 📊 Server-controlled
        goal.setStatus(GoalStatus.ACTIVE);

        return goalRepository.save(goal);
    }

    // =========================
    // STEP 6 — UPDATE GOAL
    // =========================
    public Goal updateGoal(Long goalId, Long userId, UpdateGoalRequest request) {

        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Goal not found"));

        // 🔐 Ownership enforcement
        if (!Objects.equals(goal.getUserId(), userId)) {
            throw new RuntimeException("You are not allowed to update this goal");
        }

        if (goal.getStatus() != GoalStatus.ACTIVE) {
            throw new RuntimeException("Only active goals can be updated");
        }

        // ✅ Allowed updates only
        if (request.getDifficulty() != null) {
            goal.setDifficulty(request.getDifficulty());
        }

        if (request.getDailyMinimumEffort() != null) {
            if (request.getDailyMinimumEffort() <= 0) {
                throw new RuntimeException("Daily minimum effort must be greater than zero");
            }
            goal.setDailyMinimumEffort(request.getDailyMinimumEffort());
        }

        if (request.getVisibility() != null) {
            goal.setVisibility(request.getVisibility());
        }

        return goalRepository.save(goal);
    }

    // =========================
    // STEP 7 — COMPLETE GOAL
    // =========================
    public Goal completeGoal(Long goalId, Long userId) {

        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Goal not found"));

        if (!Objects.equals(goal.getUserId(), userId)) {
            throw new RuntimeException("You are not allowed to modify this goal");
        }

        if (goal.getStatus() != GoalStatus.ACTIVE) {
            throw new RuntimeException("Only active goals can be completed");
        }

        goal.setStatus(GoalStatus.COMPLETED);
        return goalRepository.save(goal);
    }

    // =========================
    // STEP 7 — DEACTIVATE GOAL
    // =========================
    public Goal deactivateGoal(Long goalId, Long userId) {

        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Goal not found"));

        if (!Objects.equals(goal.getUserId(), userId)) {
            throw new RuntimeException("You are not allowed to modify this goal");
        }

        if (goal.getStatus() == GoalStatus.ARCHIVED) {
            throw new RuntimeException("Goal is already inactive");
        }

        goal.setStatus(GoalStatus.ARCHIVED);
        return goalRepository.save(goal);
    }
}
