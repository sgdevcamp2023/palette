import { memo, useCallback } from 'react';
import type { FormEvent, ChangeEvent } from 'react';

import { StepTitle } from '..';
import { isValidEmail } from '@/utils';
import type { LoginInfo } from '@/@types';
import { Button, Input, Typography } from '../common';
import type { NavigationEvent } from './loginReducer';

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
  const handleSubmitForm = useCallback((e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (isValidEmail(email)) {
      onNextStep();
    }
  }, []);

  return (
    <>
      <StepTitle
        title="시작하려면 먼저 이메일 또는 사용자 아이디를 입력하세요."
        className="mt-[28px] break-keep"
      />
      <form onSubmit={handleSubmitForm} className="w-full h-full">
        <div className="flex flex-col h-full pb-[24px] overflow-hidden">
          <div className="h-full mt-[54px] flex flex-col gap-[40px]">
            <Input
              value={email}
              label="이메일 주소"
              onChange={(e) => onChangeInput(e, 'email')}
            />
          </div>

          <div className="flex justify-between items-center">
            <Typography
              size="body-1"
              color="grey-600"
              role="button"
              tabIndex={0}
              aria-label="비밀번호를 잊으셨나요?"
              className="cursor-pointer hover:text-blue-500 transition-colors"
              onClick={onClickForgetPassword}
            >
              비밀번호를 잊으셨나요?
            </Typography>
            <Button
              type="submit"
              variant="filled"
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
        </div>
      </form>
    </>
  );
}

const MemoizedLoginEmailBox = memo(LoginEmailBox);

export default MemoizedLoginEmailBox;
