import { env } from '@/constants';
import { createApiWrappers } from './handler';
import type {
  EditPaint,
  JoinInfo,
  LoginInfo,
  TimelineItem,
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
    client.public.post('/auth/resend', request),
  login: (request: LoginInfo) =>
    client.public.post<{ accessToken: string; refreshToken: string }>(
      '/auth/mobile',
      request,
    ),
  logout: () => client.private.post('/auth/web-logout'),
});

const timelines = createApiWrappers({
  getRecommendTimelineList: () =>
    client.private.get<TimelineItem[]>(`/timeline/for-you`),
  getFollowingTimelineList: () =>
    client.private.get<TimelineItem[]>(`/timeline/following`),
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
  getUserPaints: (userId: User['id']) =>
    client.private.get<TimelineItem[]>(`/users/${userId}/paint`),
  getUserMarkPaints: (userId: User['id']) =>
    client.private.get<TimelineItem[]>(`/users/${userId}/mark`),
  getUserReplyPaints: (userId: User['id']) =>
    client.private.get<TimelineItem[]>(`/users/${userId}/reply`),
  getUserMediaPaints: (userId: User['id']) =>
    client.private.get<TimelineItem[]>(`/users/${userId}/media`),
  getUserLikePaints: (userId: User['id']) =>
    client.private.get<TimelineItem[]>(`/users/${userId}/like`),
  followUser: (userId: User['id']) =>
    client.private.post(`/users/${userId}/follow`),
  unFollowUser: (userId: User['id']) =>
    client.private.delete(`/users/${userId}/follow`),
  likePaint: ({
    userId,
    paintId,
  }: {
    userId: User['id'];
    paintId: TimelineItem['id'];
  }) =>
    client.private.post<{ paintId: TimelineItem['id'] }>(
      `/users/${userId}/like`,
      { paintId },
    ),
  disLikePaint: ({
    userId,
    paintId,
  }: {
    userId: User['id'];
    paintId: TimelineItem['id'];
  }) =>
    client.private.delete<{ paintId: TimelineItem['id'] }>(
      `/users/${userId}/like/${paintId}`,
    ),
  rePaint: ({
    userId,
    paintId,
  }: {
    userId: User['id'];
    paintId: TimelineItem['id'];
  }) =>
    client.private.post<{ paintId: TimelineItem['id'] }>(
      `/users/${userId}/repaint`,
      { paintId },
    ),
  markPaint: ({
    userId,
    paintId,
  }: {
    userId: User['id'];
    paintId: TimelineItem['id'];
  }) =>
    client.private.post<{ paintId: TimelineItem['id'] }>(
      `/users/${userId}/mark`,
      { paintId },
    ),
  unMarkPaint: ({
    userId,
    paintId,
  }: {
    userId: User['id'];
    paintId: TimelineItem['id'];
  }) =>
    client.private.delete<{ paintId: TimelineItem['id'] }>(
      `/users/${userId}/mark/${paintId}`,
    ),
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

const paints = createApiWrappers({
  getPaintById: (paintId: TimelineItem['id']) =>
    client.private.get<TimelineItem>(`/paints/${paintId}`),
  getBeforePaintsById: (paintId: TimelineItem['id']) =>
    client.private.get<TimelineItem[]>(`/paints/${paintId}/before`),
  getAfterPaintsById: (paintId: TimelineItem['id']) =>
    client.private.get<TimelineItem[]>(`/paints/${paintId}/after`),
  getPaints: (paintId: TimelineItem['id']) =>
    client.private.get<TimelineItem[]>(`/paints/${paintId}`),
  createPaint: (request: EditPaint) => client.private.post('/paints', request),
  getQuotePaintList: (paintId: TimelineItem['id']) =>
    client.private.get<
      (Pick<TimelineItem, 'authorId' | 'createdAt' | 'id' | 'text'> & {
        includes: {
          paints: Pick<TimelineItem, 'authorId' | 'createdAt' | 'id' | 'text'>;
        };
      })[]
    >(`/paints/${paintId}/quote-paints`),
});

export const apis = {
  auth,
  users,
  timelines,
  images,
  paints,
} as const;
