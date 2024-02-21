import { toast } from 'react-toastify';
import { memo, useCallback, useState } from 'react';
import type { FormEvent, ChangeEvent } from 'react';
import { useMutation } from '@tanstack/react-query';

import { apis } from '@/api';
import { isValidEmail } from '@/utils';
import { NotSupportText, StepTitle } from '..';
import { Button, Input, Typography } from '../common';
import type { NavigationEvent, JoinInfo } from './joinReducer';
import { FullScreenSpinner } from '../skeleton';

interface JoinEmailBoxProps extends NavigationEvent {
  nickname: string;
  email: string;
  disabled?: boolean;
  onChangeInput: (
    e: ChangeEvent<HTMLInputElement>,
    type: keyof JoinInfo,
  ) => void;
}

function JoinEmailBox({
  nickname,
  email,
  disabled,
  onNextStep,
  onChangeInput,
}: JoinEmailBoxProps) {
  const [emailStatus, setEmailStatus] = useState<
    'normal' | 'success' | 'error'
  >('normal');

  const tempRegisterMutate = useMutation({
    mutationKey: ['temp-register', nickname],
    mutationFn: () =>
      apis.users.temporaryJoin({
        email,
        nickname,
      }),
    onError: (error) => toast(`서버에 문제가 생겼습니다. ${error.message}`),
    onSuccess: () => {
      onNextStep();
    },
  });

  const handleSubmitForm = useCallback(
    (e: FormEvent<HTMLFormElement>) => {
      e.preventDefault();

      if (isValidEmail(email) && nickname !== '') {
        tempRegisterMutate.mutate();
      }
    },
    [email, nickname],
  );

  const handleBlurOnEmail = () => {
    setEmailStatus(isValidEmail(email) ? 'success' : 'error');
  };

  return (
    <>
      <StepTitle title="계정을 생성하세요" className="mt-[28px]" />
      <form onSubmit={handleSubmitForm} className="w-full h-full">
        <div className="flex flex-col h-full pb-[24px] justify-between overflow-hidden">
          <div className="h-full mt-[50px] flex flex-col gap-[40px]">
            <Input
              value={nickname}
              label="이름"
              onChange={(e) => onChangeInput(e, 'nickname')}
            />
            <Input
              value={email}
              label="이메일"
              status={emailStatus}
              onChange={(e) => onChangeInput(e, 'email')}
              onBlur={handleBlurOnEmail}
            />
          </div>
          <Typography size="body-3" color="blueGrey-800" className="pb-[18px]">
            가입하면 <NotSupportText text="쿠키 사용" />을 포함해
            <NotSupportText text="이용약관" />및
            <NotSupportText text="개인정보 처리방침" />
            에 동의하게 됩니다. Easel는 계정을 안전하게 보호하고 광고를 포함한
            맞춤 서비스를 제공하는 등 Easel 개인정보 처리방침에 명시된 목적을
            위해 이메일 주소 및 전화번호 등의 내연락처 정보를 사용할 수
            있습니다.
            <NotSupportText text="자세히 알아보기." />
            이메일 또는 전화번호를 제공하시면 다른 사람들이 이정보로 내 계정을
            찾을 수 있게 됩니다. 해당 정보를 제공하지 않으시려면
            <NotSupportText text="여기" className="mx-0 mr-0.5" />
            에서 변경하세요.
          </Typography>
          <Button
            type="submit"
            color="blue"
            variant="filled"
            disabled={disabled || tempRegisterMutate.isPending}
            aria-disabled={disabled || tempRegisterMutate.isPending}
          >
            <Typography size="sub-headline-3" color="white">
              다음
            </Typography>
          </Button>
        </div>
      </form>
      {tempRegisterMutate.isPending && (
        <FullScreenSpinner className="left-1/2 -translate-x-1/2" />
      )}
    </>
  );
}

const MemoizedJoinEmailBox = memo(JoinEmailBox);

export default MemoizedJoinEmailBox;
