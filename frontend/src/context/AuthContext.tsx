import {
  createContext,
  useContext,
  useEffect,
  useState,
  ReactNode,
} from 'react';
import { api, AuthResponse } from '../services/api';

interface User {
  email: string;
  role: 'USER' | 'ADMIN';
}

interface AuthContextType {
  user: User | null;
  token: string | null;
  isAuthenticated: boolean;
  isLoading: boolean;
  login: (email: string, password: string) => Promise<void>;
  register: (email: string, password: string) => Promise<void>;
  logout: () => Promise<void>;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<User | null>(null);
  const [token, setToken] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  // 🔄 Restore session
  useEffect(() => {
    const restore = async () => {
      try {
        const res = await api.post<AuthResponse>('/auth/refresh');
        setToken(res.data.accessToken);
        setUser({ email: res.data.email, role: res.data.role });
        api.defaults.headers.common.Authorization =
          `Bearer ${res.data.accessToken}`;
      } catch {
        setUser(null);
        setToken(null);
      } finally {
        setIsLoading(false);
      }
    };
    restore();
  }, []);

  const login = async (email: string, password: string) => {
    const res = await api.post<AuthResponse>('/auth/login', {
      email,
      password,
    });

    setToken(res.data.accessToken);
    setUser({ email: res.data.email, role: res.data.role });

    api.defaults.headers.common.Authorization =
      `Bearer ${res.data.accessToken}`;
  };

  const register = async (email: string, password: string) => {
    await api.post('/auth/register', { email, password });
  };

  // 🔥 FIXED LOGOUT
  const logout = async () => {
    // 🔥 STEP 1: REMOVE ACCESS TOKEN IMMEDIATELY
    delete api.defaults.headers.common.Authorization;

    try {
      // 🔥 STEP 2: CALL LOGOUT WITH NO AUTH HEADER
      await api.post(
        '/auth/logout',
        {},
        {
          withCredentials: true,
          headers: {
            Authorization: '', // 👈 FORCE EMPTY HEADER
          },
        }
      );
    } catch (error) {
      console.log(
        'Logout backend call failed (likely token expired), clearing UI anyway.'
      );
    } finally {
      // 🔥 STEP 3: ALWAYS CLEAR UI STATE
      setUser(null);
      setToken(null);
      delete api.defaults.headers.common.Authorization;
    }
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

export const useAuth = () => {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error('useAuth must be used inside AuthProvider');
  return ctx;
};
