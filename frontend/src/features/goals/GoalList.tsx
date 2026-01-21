import { useEffect, useState } from "react";
import { getMyGoals, completeGoal, deactivateGoal } from "./goalService";
import { Goal } from "./types";
import { useAuth } from "../../context/AuthContext";

interface GoalListProps {
  refreshTrigger: number;
}

export default function GoalList({ refreshTrigger }: GoalListProps) {
  const { isAuthenticated, isLoading } = useAuth();
  const [goals, setGoals] = useState<Goal[]>([]);
  const [loadingGoals, setLoadingGoals] = useState(true);

  useEffect(() => {
    if (isLoading || !isAuthenticated) return;
    fetchGoals();
  }, [refreshTrigger, isAuthenticated, isLoading]);

  const fetchGoals = async () => {
    try {
      setLoadingGoals(true);
      const data = await getMyGoals();
      setGoals(data);
    } catch (err) {
      console.error("Failed to fetch goals", err);
    } finally {
      setLoadingGoals(false);
    }
  };

  if (loadingGoals) {
    return <p className="text-gray-500">Loading goals...</p>;
  }

  if (goals.length === 0) {
    return (
      <div className="text-center py-10 bg-gray-50 rounded border border-dashed border-gray-300">
        <p className="text-gray-600 text-lg font-medium">
          You haven't created any goals yet.
        </p>
        <p className="text-gray-400 text-sm mt-1">
          Use the form on the left to start your journey 🚀
        </p>
      </div>
    );
  }

  return (
    <div className="space-y-4">
      {goals.map(goal => (
        <div
          key={goal.id}
          className="p-4 bg-white border rounded shadow flex justify-between"
        >
          <div>
            <h3 className="font-bold">{goal.category}</h3>
            <p className="text-sm text-gray-600">
              {goal.difficulty} • {goal.dailyMinimumEffort} min/day
            </p>
            <p className="text-xs text-gray-500">
              {goal.startDate} → {goal.endDate}
            </p>
            <span className="text-xs font-semibold">
              Status: {goal.status}
            </span>
          </div>

          {goal.status === "ACTIVE" && (
            <div className="flex flex-col gap-2">
              <button
                onClick={() => completeGoal(goal.id)}
                className="bg-green-500 text-white text-xs px-3 py-1 rounded"
              >
                Complete
              </button>
              <button
                onClick={() => deactivateGoal(goal.id)}
                className="bg-gray-400 text-white text-xs px-3 py-1 rounded"
              >
                Archive
              </button>
            </div>
          )}
        </div>
      ))}
    </div>
  );
}
