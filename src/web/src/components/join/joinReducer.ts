import type { Dispatch, Reducer } from 'react';

export enum JoinStep {
  INFORMATION = 1,
  EMAIL_VERIFY,
  PASSWORD,
  PROFILE_IMAGE,
  NAME,
}

export const joinStepReducer: Reducer<
  JoinStep,
  { direction: 'prev' | 'next' }
> = (state, action) => {
  switch (action.direction) {
    case 'prev':
      return Math.max(state - 1, JoinStep.INFORMATION);
    case 'next':
      return Math.min(state + 1, JoinStep.NAME);
    default:
      return state;
  }
};

export type JoinDispatcher = Dispatch<{
  direction: 'next' | 'prev';
}>;

export interface NavigationEvent {
  onNextStep: VoidFunction;
}

export interface JoinInfo {
  username: string;
  nickname: string;
  email: string;
  emailVerifyCode: string;
  password: string;
  profileImagePath: string;
}
