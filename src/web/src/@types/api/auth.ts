export interface Token {
  accessToken: string;
  refreshToken: string;
}

export interface User {
  id: string;
  email: string;
  password: string;
  profileImagePath: string;
  backgroundImagePath: string;
  status: 'private' | 'public';
  username: string;
  nickname: string;
  introduce: string;
  websitePath: string;
  joinedAt: string;
  followerCount: number;
  followingCount: number;
}

export type UserSearchResult = Pick<User, 'id' | 'username' | 'nickname'> & {
  imagePath: User['profileImagePath'];
  status?: User['status'];
  isFollowing?: boolean;
};
export type UserProfile = Pick<
  User,
  | 'id'
  | 'backgroundImagePath'
  | 'profileImagePath'
  | 'nickname'
  | 'username'
  | 'introduce'
  | 'websitePath'
  | 'joinedAt'
  | 'followerCount'
  | 'followingCount'
>;
export type LoginInfo = Pick<User, 'email' | 'password'>;
export type JoinInfo = Pick<User, 'email' | 'nickname'> &
  Partial<Pick<User, 'password' | 'username' | 'profileImagePath'>>;
