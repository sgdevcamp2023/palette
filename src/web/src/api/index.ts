import type { LoginInfo, User } from '@/@types';
import { createApiClient } from './apiFactory';
import { createApiWrappers } from './handler';

const client = {
  public: createApiClient({ auth: false }),
  private: createApiClient({ auth: true }),
} as const;

const auth = createApiWrappers({
  passport: () => client.private.post('/auth/passport'),
  login: ({ email, password }: LoginInfo) =>
    client.public.post('/auth/web', {
      email,
      password,
    }),
});

const users = createApiWrappers({
  verifyEmail: ({ email }: { email: User['email'] }) =>
    client.public.post<{ isDuplicated: boolean }>('/users/verify-email', {
      email,
    }),
  verifyUsername: ({ username }: { username: User['username'] }) =>
    client.public.post<{ isDuplicated: boolean }>('/users/verify-username', {
      username,
    }),
  join: ({ username }: { username: User['username'] }) =>
    client.public.post<{ isDuplicated: boolean }>('/users/verify-username', {
      username,
    }),
  logout: () => client.private.post('/users/web-logout'),
});

export const apis = {
  auth,
  users,
} as const;
