import type { Dispatch } from 'react';

export enum LoginStep {
  EMAIL = 1,
  PASSWORD,
}

export const LoginStepReducer: React.Reducer<
  LoginStep,
  { direction: 'prev' | 'next' }
> = (state, action) => {
  switch (action.direction) {
    case 'prev':
      return Math.max(state - 1, LoginStep.EMAIL);
    case 'next':
      return Math.min(state + 1, LoginStep.PASSWORD);
    default:
      return state;
  }
};

export type LoginStepDispatcher = Dispatch<{
  direction: 'next' | 'prev';
}>;

export interface NavigationEvent {
  onNextStep: VoidFunction;
}

export interface LoginInfo {
  email: string;
  password: string;
}
