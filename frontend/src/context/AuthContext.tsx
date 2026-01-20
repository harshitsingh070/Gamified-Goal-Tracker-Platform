import React, {
  createContext,
  useContext,
  useState,
  useEffect,
  ReactNode,
} from 'react';
import { api, AuthResponse, JwtPayload } from '../services/api';
import { jwtDecode } from 'jwt-decode';

interface AuthUser {
  email: string;
  role: 'USER' | 'ADMIN';
}

interface AuthContextType {
  user: AuthUser | null;
  token: string | null;
  isAuthenticated: boolean;
  isLoading: boolean;
  login: (email: string, password: string) => Promise<void>;
  register: (email: string, password: string) => Promise<void>;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<AuthUser | null>(null);
  const [token, setToken] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    setIsLoading(false);
  }, []);

  // ---------- LOGIN ----------
  const login = async (email: string, password: string) => {
    try {
      const response = await api.post<AuthResponse>('/auth/login', {
        email,
        password,
      });

      const { accessToken } = response.data;

      const decoded = jwtDecode<JwtPayload>(accessToken);

      if (!decoded.sub) {
        throw new Error('Invalid token payload');
      }

      setToken(accessToken);
      setUser({
        email: decoded.sub,
        role: decoded.role ?? 'USER', // ✅ fallback safety
      });

      api.defaults.headers.common.Authorization = `Bearer ${accessToken}`;
    } catch (err) {
      console.error('Login failed:', err);
      throw err;
    }
  };

  // ---------- REGISTER ----------
  const register = async (email: string, password: string) => {
    try {
      await api.post('/auth/register', { email, password });
    } catch (err) {
      console.error('Registration failed:', err);
      throw err;
    }
  };

  // ---------- LOGOUT ----------
  const logout = () => {
    setUser(null);
    setToken(null);
    delete api.defaults.headers.common.Authorization;
  };

  return (
    <AuthContext.Provider
      value={{
        user,
        token,
        isAuthenticated: !!user,
        isLoading,
        login,
        register,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = (): AuthContextType => {
  const ctx = useContext(AuthContext);
  if (!ctx) {
    throw new Error('useAuth must be used inside AuthProvider');
  }
  return ctx;
};
