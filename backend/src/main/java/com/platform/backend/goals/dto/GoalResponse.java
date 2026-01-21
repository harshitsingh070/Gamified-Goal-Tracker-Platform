package com.platform.backend.goals.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoalResponse {

    private Long id;
    private String title;
    private String category;
    private String difficulty;
    private String visibility;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
}
