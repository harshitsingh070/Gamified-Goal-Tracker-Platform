import { useState } from "react";
import { createGoal } from "./goalService";
import {
  GoalCategory,
  GoalDifficulty,
  GoalVisibility,
} from "./types";

/* 🔒 MUST MATCH BACKEND ENUM EXACTLY */
const categories: GoalCategory[] = [
  "CODING",
  "HEALTH",
  "FITNESS",
  "READING",
  "ACADEMICS",
  "CAREER",
  "PERSONAL_GROWTH",
];


const difficulties: GoalDifficulty[] = ["EASY", "MEDIUM", "HARD"];

interface Props {
  onSuccess?: () => void;
}

export default function CreateGoalForm({ onSuccess }: Props) {
  const [category, setCategory] = useState<GoalCategory>("CODING");
  const [difficulty, setDifficulty] = useState<GoalDifficulty>("MEDIUM");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [dailyMinimumEffort, setDailyMinimumEffort] = useState(30);
  const [visibility, setVisibility] = useState<GoalVisibility>("PRIVATE");
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);

    /* 🛑 Basic frontend validation */
    if (!startDate || !endDate) {
      setError("Start date and end date are required");
      return;
    }

    if (endDate < startDate) {
      setError("End date cannot be before start date");
      return;
    }

    try {
      setLoading(true);

      await createGoal({
        category,
        difficulty,
        startDate,
        endDate,
        dailyMinimumEffort,
        visibility,
      });

      alert("Goal created successfully!");

      /* 🔔 Notify parent (Dashboard) */
      onSuccess?.();

      /* Optional: reset form */
      setStartDate("");
      setEndDate("");
      setDailyMinimumEffort(30);
      setVisibility("PRIVATE");

    } catch (err: any) {
      setError(err.response?.data?.message || "Failed to create goal");
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4 max-w-md">
      <h2 className="text-xl font-bold">Create Goal</h2>

      <select
        value={category}
        onChange={e => setCategory(e.target.value as GoalCategory)}
        className="w-full border p-2 rounded"
      >
        {categories.map(c => (
          <option key={c} value={c}>{c}</option>
        ))}
      </select>

      <select
        value={difficulty}
        onChange={e => setDifficulty(e.target.value as GoalDifficulty)}
        className="w-full border p-2 rounded"
      >
        {difficulties.map(d => (
          <option key={d} value={d}>{d}</option>
        ))}
      </select>

      <input
        type="date"
        value={startDate}
        onChange={e => setStartDate(e.target.value)}
        className="w-full border p-2 rounded"
      />

      <input
        type="date"
        value={endDate}
        onChange={e => setEndDate(e.target.value)}
        className="w-full border p-2 rounded"
      />

      <input
        type="number"
        min={1}
        value={dailyMinimumEffort}
        onChange={e => setDailyMinimumEffort(Number(e.target.value))}
        className="w-full border p-2 rounded"
      />

      <select
        value={visibility}
        onChange={e => setVisibility(e.target.value as GoalVisibility)}
        className="w-full border p-2 rounded"
      >
        <option value="PRIVATE">PRIVATE</option>
        <option value="PUBLIC">PUBLIC</option>
      </select>

      {error && <p className="text-red-500 text-sm">{error}</p>}

      <button
        type="submit"
        disabled={loading}
        className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded"
      >
        {loading ? "Creating..." : "Create Goal"}
      </button>
    </form>
  );
}
