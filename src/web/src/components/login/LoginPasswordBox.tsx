import { toast } from 'react-toastify';
import { useMutation } from '@tanstack/react-query';
import { useNavigate } from '@tanstack/react-router';
import { memo, useCallback, useState } from 'react';
import type { FormEvent, ChangeEvent } from 'react';

import { StepTitle } from '..';
import type { LoginInfo } from '@/@types';
import { Button, Input, Typography } from '../common';
import {
  accessTokenStorage,
  refreshTokenStorage,
} from '@/api/AuthTokenStorage';
import { apis } from '@/api';
import { FullScreenSpinner } from '../skeleton';

interface LoginPasswordBoxProps {
  email: string;
  password: string;
  disabled?: boolean;
  onChangeInput: (
    e: ChangeEvent<HTMLInputElement>,
    type: keyof LoginInfo,
  ) => void;
  onClickForgetPassword: VoidFunction;
}

function LoginPasswordBox({
  email,
  password,
  disabled,
  onChangeInput,
  onClickForgetPassword,
}: LoginPasswordBoxProps) {
  const navigate = useNavigate();
  const [isHidden, setIsHidden] = useState<boolean>(true);

  const loginMutate = useMutation({
    mutationKey: ['login', email],
    mutationFn: () => apis.auth.login({ email, password }),
    onSuccess: (res) => {
      toast(`${email}님 로그인이 완료되었습니다.`);
      accessTokenStorage.setToken(res.accessToken);
      refreshTokenStorage.setToken(res.refreshToken);
      navigate({ to: '/home' });
    },
    onError: () => toast.error('아이디 혹은 비밀번호가 틀렸습니다.'),
  });

  const handleSubmitForm = useCallback(
    (e: FormEvent<HTMLFormElement>) => {
      e.preventDefault();

      if (password !== '') {
        loginMutate.mutate();
      }
    },
    [password],
  );

  return (
    <>
      <StepTitle
        title="시작하려면 먼저 이메일 또는 사용자 아이디를 입력하세요."
        className="mt-[28px] break-keep"
      />
      <form onSubmit={handleSubmitForm} className="w-full">
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

            <div className="text-center">
              <Button
                type="submit"
                variant="filled"
                disabled={disabled}
                aria-disabled={disabled}
              >
                <Typography size="body-2" color="white">
                  로그인하기
                </Typography>
              </Button>
            </div>
          </div>
        </div>
      </form>
      <Button
        onClick={onClickForgetPassword}
        className="cursor-pointer hover:text-blue-500 transition-colors bg-transparent"
      >
        <Typography
          size="body-1"
          color="grey-600"
          aria-label="비밀번호를 잊으셨나요?"
        >
          비밀번호를 잊으셨나요?
        </Typography>
      </Button>

      {loginMutate.isPending && (
        <FullScreenSpinner className="left-1/2 -translate-x-1/2" />
      )}
    </>
  );
}

const MemoizedLoginPasswordBox = memo(LoginPasswordBox);

export default MemoizedLoginPasswordBox;
