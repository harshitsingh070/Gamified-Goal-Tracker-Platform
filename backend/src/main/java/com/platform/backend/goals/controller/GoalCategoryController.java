package com.platform.backend.goals.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platform.backend.goals.domain.GoalCategory;

@RestController
@RequestMapping("/api/goals/categories")
public class GoalCategoryController {

    @GetMapping
    public List<String> getAllCategories() {
        return Arrays.stream(GoalCategory.values())
                .map(Enum::name)
                .toList();
    }
}
