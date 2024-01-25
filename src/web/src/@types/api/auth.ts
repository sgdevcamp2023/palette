import type { User } from '../models';

export interface Passport {
  passport: string;
}

export type LoginInfo = Pick<User, 'email' | 'password'>;
export type JoinInfo = Pick<User, 'email' | 'nickname'> &
  Partial<Pick<User, 'password' | 'username' | 'profileImagePath'>>;
