import { memo, useCallback, useState } from 'react';
import type { FormEvent, ChangeEvent } from 'react';

import { StepTitle } from '..';
import type { LoginInfo } from '@/@types';
import { Button, Input, Typography } from '../common';

interface LoginPasswordBoxProps {
  email: string;
  password: string;
  disabled?: boolean;
  onChangeInput: (
    e: ChangeEvent<HTMLInputElement>,
    type: keyof LoginInfo,
  ) => void;
  onLogin: VoidFunction;
  onClickForgetPassword: VoidFunction;
}

function LoginPasswordBox({
  email,
  password,
  disabled,
  onChangeInput,
  onLogin,
  onClickForgetPassword,
}: LoginPasswordBoxProps) {
  const [isHidden, setIsHidden] = useState<boolean>(true);

  const handleSubmitForm = useCallback((e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (password !== '') {
      onLogin();
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
          <div className="flex flex-col gap-[100px]">
            <div className="mt-[54px] flex flex-col gap-[40px]">
              <Input
                value={email}
                label="이메일 주소 또는 사용자 아이디"
                onChange={(e) => onChangeInput(e, 'email')}
                readOnly
                aria-readonly
              />
              <Input
                value={password}
                label="비밀번호"
                type={isHidden ? 'password' : 'text'}
                onChange={(e) => onChangeInput(e, 'password')}
                icon={{
                  type: isHidden ? 'eyeOff' : 'eyeOn',
                  stroke: 'blueGrey-400',
                  onClick: () => setIsHidden((prev) => !prev),
                }}
              />
            </div>

            <div className="flex flex-col gap-[28px] items-center">
              <Button
                type="submit"
                variant="filled"
                disabled={disabled}
                aria-disabled={disabled}
                onClick={onLogin}
              >
                <Typography size="body-2" color="white">
                  로그인하기
                </Typography>
              </Button>
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
            </div>
          </div>
        </div>
      </form>
    </>
  );
}

const MemoizedLoginPasswordBox = memo(LoginPasswordBox);

export default MemoizedLoginPasswordBox;
