import axios from 'axios';
import { authTokenStorage } from './AuthTokenStorage';

export const createApiClient = ({ auth }: { auth: boolean }) => {
  const client = axios.create({
    baseURL: `${import.meta.env.VITE_BASE_SERVER_URL}/v1`,
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

export const cdnAPIClient = () => {
  const client = axios.create({
    baseURL: `${import.meta.env.VITE_CDN_BASE_URL}/${
      import.meta.env.VITE_CLOUD_NAME
    }/`,
  });

  return client;
};
