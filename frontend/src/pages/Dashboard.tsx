import { useAuth } from "../context/AuthContext";
import CreateGoalForm from "../features/goals/CreateGoalForm";

export default function Dashboard() {
  const { user, logout } = useAuth();

  return (
    <div className="min-h-screen bg-gray-50 p-8">
      {/* 🟢 Header Section */}
      <div className="flex justify-between items-center mb-8 bg-white p-4 rounded shadow">
        <div>
          <h1 className="text-2xl font-bold text-gray-800">Dashboard</h1>
          <p className="text-gray-600">User: {user?.email}</p>
        </div>
        <button
          onClick={logout}
          className="bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded transition"
        >
          Logout
        </button>
      </div>

      {/* 🟢 Main Content Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
        
        {/* Left: Create Goal Form */}
        <div>
           <CreateGoalForm />
        </div>

        {/* Right: Goal List (Coming Soon) */}
        <div className="bg-white p-6 rounded shadow h-full">
          <h2 className="text-xl font-bold mb-4">My Goals</h2>
          <p className="text-gray-500 italic">
            List of goals will appear here...
          </p>
        </div>

      </div>
    </div>
  );
}