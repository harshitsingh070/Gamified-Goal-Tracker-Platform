import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { AuthProvider, useAuth } from './context/AuthContext';
import Login from './pages/Login';
import Register from './pages/Register';
import ProtectedRoute from './components/ProtectedRoute';

const Dashboard = () => {
  const { logout, user } = useAuth();
  return (
    <div className="p-6">
      <h1 className="text-xl font-bold">Dashboard</h1>
      <p>Email: {user?.email}</p>
      <button
        onClick={logout}
        className="mt-4 bg-red-600 p-2 text-white"
      >
        Logout
      </button>
    </div>
  );
};

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />

          <Route element={<ProtectedRoute />}>
            <Route path="/dashboard" element={<Dashboard />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}
