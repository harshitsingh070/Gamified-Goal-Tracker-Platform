package com.platform.backend.goals.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platform.backend.goals.dto.CreateGoalRequest;
import com.platform.backend.goals.dto.UpdateGoalRequest;
import com.platform.backend.goals.model.Goal;
import com.platform.backend.goals.service.GoalService;
import com.platform.backend.identity.model.User;
import com.platform.backend.identity.repository.UserRepository;

@RestController
@RequestMapping("/api/goals")
public class GoalController {

    private final GoalService goalService;
    private final UserRepository userRepository;

    public GoalController(GoalService goalService, UserRepository userRepository) {
        this.goalService = goalService;
        this.userRepository = userRepository;
    }

    // =========================
    // STEP 4 — CREATE GOAL
    // =========================
    @PostMapping
    public ResponseEntity<Goal> createGoal(@RequestBody CreateGoalRequest request) {

        User user = getAuthenticatedUser();
        Goal createdGoal = goalService.createGoal(user.getId(), request);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdGoal);
    }

    // =========================
    // STEP 5 — GET MY GOALS (WITH DEBUG LOGS)
    // =========================
    @GetMapping
    public ResponseEntity<List<Goal>> getMyGoals() {
        
        User user = getAuthenticatedUser();
        List<Goal> goals = goalService.getUserGoals(user.getId());


        return ResponseEntity.ok(goals);
    }

    // =========================
    // STEP 6 — UPDATE GOAL
    // =========================
    @PutMapping("/{goalId}")
    public ResponseEntity<Goal> updateGoal(
            @PathVariable Long goalId,
            @RequestBody UpdateGoalRequest request
    ) {

        User user = getAuthenticatedUser();
        Goal updatedGoal = goalService.updateGoal(goalId, user.getId(), request);

        return ResponseEntity.ok(updatedGoal);
    }

    // =========================
    // STEP 7 — COMPLETE GOAL
    // =========================
    @PutMapping("/{goalId}/complete")
    public ResponseEntity<Goal> completeGoal(@PathVariable Long goalId) {

        User user = getAuthenticatedUser();
        return ResponseEntity.ok(
                goalService.completeGoal(goalId, user.getId())
        );
    }

    // =========================
    // STEP 7 — DEACTIVATE GOAL
    // =========================
    @PutMapping("/{goalId}/deactivate")
    public ResponseEntity<Goal> deactivateGoal(@PathVariable Long goalId) {

        User user = getAuthenticatedUser();
        return ResponseEntity.ok(
                goalService.deactivateGoal(goalId, user.getId())
        );
    }

    // =========================
    // 🔐 Shared Auth Helper
    // =========================
    private User getAuthenticatedUser() {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Unauthorized");
        }

        String email = auth.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found: " + email)
                );
    }
}