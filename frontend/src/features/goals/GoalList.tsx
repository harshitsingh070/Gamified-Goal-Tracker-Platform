import { useEffect, useState } from "react";
import { fetchMyGoals } from "./goalService";
import { Goal } from "./types";


interface GoalListProps {
  refreshTrigger: number;
}

export default function GoalList({ refreshTrigger }: GoalListProps) {
  const [goals, setGoals] = useState<Goal[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchGoals();
  }, [refreshTrigger]);

  const fetchGoals = async () => {
    try {
      setLoading(true);
      const data = await fetchMyGoals();

      setGoals(data);
    } catch (error) {
      console.error("Failed to fetch goals", error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <p className="text-gray-500">Loading goals...</p>;
  }

  if (goals.length === 0) {
    return <p className="text-gray-500 italic">No goals found. Create one!</p>;
  }

  return (
    <div className="space-y-4">
      {goals.map((goal) => (
        <div
          key={goal.id}
          className="p-4 border rounded shadow bg-white"
        >
          <h3 className="font-bold text-lg">{goal.category}</h3>

          <p className="text-sm text-gray-600">
            {goal.difficulty} • {goal.dailyMinimumEffort} mins/day
          </p>

          <p className="text-xs text-gray-500">
            {goal.startDate} → {goal.endDate}
          </p>

          <span className="inline-block mt-2 text-xs font-semibold px-2 py-1 rounded bg-blue-100 text-blue-800">
            {goal.status}
          </span>
        </div>
      ))}
    </div>
  );
}
