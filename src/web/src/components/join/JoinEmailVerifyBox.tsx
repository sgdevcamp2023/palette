import { toast } from 'react-toastify';
import { memo, useCallback } from 'react';
import type { FormEvent, ChangeEvent } from 'react';
import { useMutation } from '@tanstack/react-query';

import { apis } from '@/api';
import { StepTitle } from '..';
import { Button, Input, Typography } from '../common';
import type { NavigationEvent, JoinInfo } from './joinReducer';
import { FullScreenSpinner } from '../skeleton';

interface JoinEmailVerifyBoxProps extends NavigationEvent {
  email: string;
  emailVerifyCode: string;
  disabled: boolean;
  onChangeInput: (
    e: ChangeEvent<HTMLInputElement>,
    type: keyof JoinInfo,
  ) => void;
}

function JoinEmailVerifyBox({
  email,
  emailVerifyCode,
  disabled,
  onNextStep,
  onChangeInput,
}: JoinEmailVerifyBoxProps) {
  const verifyEmailCodeMutate = useMutation({
    mutationKey: ['verify-email', email],
    mutationFn: () =>
      apis.auth.verifyEmailCode({
        email,
        payload: emailVerifyCode,
      }),
    onError: () => toast('인증코드가 다릅니다.'),
    onSuccess: () => {
      onNextStep();
    },
  });

  const handleSubmitForm = useCallback((e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    verifyEmailCodeMutate.mutate();
  }, []);

  const reSendEmailMutate = useMutation({
    mutationKey: ['re-send', email],
    mutationFn: () => apis.auth.reSendEmailCode({ email }),
    onSettled: () => {
      toast('이메일이 다시 발송되었습니다.');
    },
  });

  return (
    <>
      <StepTitle
        title="코드를 보내드렸습니다"
        description={`${email} 인증을 위해 아래에 입력하세요`}
        className="mt-[28px]"
      />
      <form onSubmit={handleSubmitForm} className="w-full h-full">
        <div className="flex flex-col h-full pb-[24px] justify-between overflow-hidden">
          <div className="h-full mt-[50px] flex flex-col gap-[40px]">
            <Input
              label="인증코드"
              value={emailVerifyCode}
              maxLength={6}
              minLength={6}
              onChange={(e) => onChangeInput(e, 'emailVerifyCode')}
            />
          </div>
          <div className="flex flex-col gap-[18px]">
            <Typography
              as="span"
              size="body-3"
              role="button"
              tabIndex={0}
              color="blue-500"
              onClick={() => reSendEmailMutate.mutate()}
            >
              이메일을 받지 못하셨나요?
            </Typography>
            <Button
              type="submit"
              color="blue"
              variant="filled"
              disabled={disabled || verifyEmailCodeMutate.isPending}
              aria-disabled={disabled || verifyEmailCodeMutate.isPending}
            >
              <Typography size="body-2" color="white">
                다음
              </Typography>
            </Button>
          </div>
        </div>
      </form>
      {verifyEmailCodeMutate.isPending && (
        <FullScreenSpinner className="left-1/2 -translate-x-1/2" />
      )}
    </>
  );
}

const MemoizedJoinEmailVerifyBox = memo(JoinEmailVerifyBox);

export default MemoizedJoinEmailVerifyBox;
