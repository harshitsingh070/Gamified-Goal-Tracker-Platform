export type GoalCategory =
  | "CODING"
  | "HEALTH"
  | "FITNESS"
  | "READING"
  | "ACADEMICS"
  | "CAREER"
  | "PERSONAL_GROWTH";


export type GoalDifficulty = "EASY" | "MEDIUM" | "HARD";

export type GoalVisibility = "PRIVATE" | "PUBLIC";

export interface CreateGoalRequest {
  category: GoalCategory;
  difficulty: GoalDifficulty;
  startDate: string; // YYYY-MM-DD
  endDate: string;   // YYYY-MM-DD
  dailyMinimumEffort: number;
  visibility?: GoalVisibility;
}

export interface Goal {
  id: number;
  category: GoalCategory;
  difficulty: GoalDifficulty;
  startDate: string;
  endDate: string;
  dailyMinimumEffort: number;
  visibility: GoalVisibility;
  status: "ACTIVE" | "COMPLETED" | "ARCHIVED";
  createdAt: string;
}
