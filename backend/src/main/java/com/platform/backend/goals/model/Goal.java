package com.platform.backend.goals.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.platform.backend.goals.domain.GoalCategory;
import com.platform.backend.goals.domain.GoalDifficulty;
import com.platform.backend.goals.domain.GoalStatus;
import com.platform.backend.goals.domain.GoalVisibility;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "goals")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Owner (User.id) – no User object to avoid coupling
    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private GoalCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private GoalDifficulty difficulty;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Integer dailyMinimumEffort;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private GoalVisibility visibility = GoalVisibility.PRIVATE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private GoalStatus status = GoalStatus.ACTIVE;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    /* ---------- Getters & Setters ---------- */

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public GoalStatus getStatus() {
        return status;
    }

    public void setStatus(GoalStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
