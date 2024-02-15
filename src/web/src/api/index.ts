import { env } from '@/constants';
import { createApiWrappers } from './handler';
import type {
  JoinInfo,
  LoginInfo,
  User,
  UserProfile,
  UserSearchResult,
} from '@/@types';
import { cdnAPIClient, createApiClient } from './apiFactory';

const client = {
  public: createApiClient({ auth: false }),
  private: createApiClient({ auth: true }),
  cdn: cdnAPIClient(),
} as const;

const auth = createApiWrappers({
  verifyEmailCode: (request: { email: User['email']; payload: string }) =>
    client.public.post('/auth', request),
  reSendEmailCode: (request: { email: User['email'] }) =>
    client.public.post('/auth/re-send', request),
  login: (request: LoginInfo) => client.public.post('/auth/web', request),
  logout: () => client.private.post('/auth/web-logout'),
});

const users = createApiWrappers({
  checkDuplicateEmail: ({ email }: { email: User['email'] }) =>
    client.public.post<{ isDuplicated: boolean }>('/users/verify-email', {
      email,
    }),
  checkDuplicateUsername: ({ username }: { username: User['username'] }) =>
    client.public.post<{ isDuplicated: boolean }>('/users/verify-username', {
      username,
    }),
  join: (
    request: Pick<User, 'email' | 'password' | 'username' | 'profileImagePath'>,
  ) =>
    client.public.post('/users/join', {
      ...request,
      introduce: null,
      backgroundPath: null,
      websitePath: null,
    }),
  temporaryJoin: (request: Pick<JoinInfo, 'email' | 'nickname'>) =>
    client.public.post('/users/temporary-join', request),
  getUserProfile: (userId: User['id']) =>
    client.private.get<UserProfile>(`/users/${userId}`),
  searchUserByDisplayName: (keyword: string) =>
    client.private.get<UserSearchResult[]>(`/users/search?keyword=${keyword}`),
  getMyProfile: () => client.private.get<UserProfile>(`/users/me`),
  updateProfile: (
    request: Pick<
      User,
      | 'nickname'
      | 'introduce'
      | 'profileImagePath'
      | 'backgroundImagePath'
      | 'websitePath'
    >,
  ) =>
    client.public.put<
      Pick<
        User,
        | 'email'
        | 'profileImagePath'
        | 'backgroundImagePath'
        | 'username'
        | 'nickname'
        | 'introduce'
        | 'websitePath'
      > & { userId: User['id'] }
    >('/users/profile', request),
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
