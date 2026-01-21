import { api } from "../../services/api";
import { CreateGoalRequest, Goal } from "./types";

export const createGoal = async (
  payload: CreateGoalRequest
): Promise<Goal> => {
  const response = await api.post<Goal>("/goals", payload);
  return response.data;
};

export const fetchMyGoals = async (): Promise<Goal[]> => {
  const response = await api.get<Goal[]>("/goals");
  return response.data;
};

