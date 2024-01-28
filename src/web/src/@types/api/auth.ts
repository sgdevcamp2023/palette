export interface Passport {
  passport: string;
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
  createdAt: Date;
  followers: number;
  followings: number;
}

export type LoginInfo = Pick<User, 'email' | 'password'>;
export type JoinInfo = Pick<User, 'email' | 'nickname'> &
  Partial<Pick<User, 'password' | 'username' | 'profileImagePath'>>;
