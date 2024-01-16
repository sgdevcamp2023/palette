import type { Dispatch } from 'react';

export enum SignUpStep {
  INFORMATION = 1,
  EMAIL_VERIFY,
  PASSWORD,
  PROFILE_IMAGE,
  NAME,
}

export const SignUpStepReducer: React.Reducer<
  SignUpStep,
  { direction: 'prev' | 'next' }
> = (state, action) => {
  switch (action.direction) {
    case 'prev':
      return Math.max(state - 1, SignUpStep.INFORMATION);
    case 'next':
      return Math.min(state + 1, SignUpStep.NAME);
    default:
      return state;
  }
};

export type SignUpStepDispatcher = Dispatch<{
  direction: 'next' | 'prev';
}>;

export interface NavigationEvent {
  onNextStep: VoidFunction;
}

export interface SignUpInfo {
  username: string;
  nickname: string;
  email: string;
  emailVerifyCode: string;
  password: string;
  profilePath: string;
}
