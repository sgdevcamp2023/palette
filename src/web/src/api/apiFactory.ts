import axios from 'axios';
import { authTokenStorage } from './AuthTokenStorage';

export const createApiClient = ({ auth }: { auth: boolean }) => {
  const client = axios.create({
    baseURL: `${import.meta.env.VITE_BASE_SERVE_URL}/v1`,
  });

  if (auth) {
    client.interceptors.request.use((config) => {
      const token = authTokenStorage.getTokenOrThrow();
      config.headers.Authorization = `Bearer ${token}`;
      return config;
    });
  }

  return client;
};
