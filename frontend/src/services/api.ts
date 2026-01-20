import axios from 'axios';

export const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

/* ======================
   AUTH TYPES
====================== */

export interface AuthResponse {
  accessToken: string;
  refreshToken?: string;
}

export interface JwtPayload {
  sub: string;        // email
  role?: 'USER' | 'ADMIN'; // optional (backend-safe)
  iat?: number;
  exp?: number;
}
