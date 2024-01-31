import { env } from '@/constants';
import { createApiWrappers } from './handler';
import type { LoginInfo, User } from '@/@types';
import { cdnAPIClient, createApiClient } from './apiFactory';

const client = {
  public: createApiClient({ auth: false }),
  private: createApiClient({ auth: true }),
  cdn: cdnAPIClient(),
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

const images = createApiWrappers({
  uploadImage: (
    image: File,
    timestamp: number,
    options: { folder?: string } = {},
  ) => {
    const formData = new FormData();
    formData.append('api_key', env.VITE_CLD_API_KEY);
    formData.append('upload_preset', env.VITE_CLD_PRESET_NAME);
    formData.append('file', image);
    formData.append('timestamp', String(Math.round(timestamp / 1000)));
    if (options.folder) {
      formData.append('folder', options.folder);
    }

    return client.cdn.post<{
      access_mode: 'public';
      asset_id: string;
      bytes: number;
      created_at: string;
      etag: string;
      folder: string;
      format: 'jpg' | 'jpeg' | 'png';
      height: number;
      original_filename: string;
      placeholder: false;
      public_id: string;
      resource_type: 'image' | 'video';
      secure_url: string;
      signature: string;
      tags: [];
      type: 'upload';
      url: string;
      version: number;
      version_id: string;
      width: number;
    }>('image/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  },
});

export const apis = {
  auth,
  users,
  images,
} as const;
