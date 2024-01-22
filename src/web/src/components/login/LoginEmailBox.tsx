import type { ChangeEvent } from 'react';

import { StepTitle } from '..';
import { Button, Input, Typography } from '../common';
import type { LoginInfo, NavigationEvent } from './loginReducer';

interface LoginEmailBoxProps extends NavigationEvent {
  email: string;
  disabled?: boolean;
  onChangeInput: (
    e: ChangeEvent<HTMLInputElement>,
    type: keyof LoginInfo,
  ) => void;
  onClickForgetPassword: VoidFunction;
}

function LoginEmailBox({
  email,
  disabled,
  onNextStep,
  onChangeInput,
  onClickForgetPassword,
}: LoginEmailBoxProps) {
  return (
    <>
      <StepTitle
        title="시작하려면 먼저 이메일 또는 사용자 아이디를 입력하세요."
        className="mt-[28px] break-keep"
      />
      <div className="h-full mt-[54px] flex flex-col gap-[40px]">
        <Input
          value={email}
          label="이메일 주소 또는 사용자 아이디"
          onChange={(e) => onChangeInput(e, 'email')}
        />
      </div>

      <div className="flex justify-between items-center">
        <Typography
          size="body-1"
          color="grey-600"
          role="navigation"
          aria-label="비밀번호를 잊으셨나요?"
          className="cursor-pointer hover:text-blue-500 transition-colors"
          onClick={onClickForgetPassword}
        >
          비밀번호를 잊으셨나요?
        </Typography>
        <Button
          variant="filled"
          aria-label="패스워드 입력 창으로 이동합니다."
          className="w-[55px] py-[6px]"
          disabled={disabled}
          aria-disabled={disabled}
          onClick={onNextStep}
        >
          <Typography size="body-2" color="white">
            다음
          </Typography>
        </Button>
      </div>
    </>
  );
}

export default LoginEmailBox;
