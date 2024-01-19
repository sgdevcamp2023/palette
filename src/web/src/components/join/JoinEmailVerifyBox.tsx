import { toast } from 'react-toastify';
import { useState, type ChangeEvent } from 'react';

import { StepTitle } from '..';
import { Button, Input, Typography } from '../common';
import type { NavigationEvent, JoinInfo } from './joinReducer';

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
  const [isDirty, setIsDirty] = useState<boolean>(false);

  const handleClickResendButton = () => {
    toast('아직 구현이 안되어 있어요!');
  };

  return (
    <>
      <StepTitle
        title="코드를 보내드렸습니다"
        description={`${email} 인증을 위해 아래에 입력하세요`}
        className="mt-[28px]"
      />
      <div className="h-full mt-[50px] flex flex-col gap-[40px]">
        <Input
          label="인증코드"
          value={emailVerifyCode}
          maxLength={6}
          minLength={6}
          status={isDirty ? 'dirty' : 'normal'}
          onFocus={() => setIsDirty(true)}
          onBlur={() => setIsDirty(false)}
          onChange={(e) => onChangeInput(e, 'emailVerifyCode')}
        />
      </div>
      <div className="flex flex-col gap-[18px]">
        <Typography
          as="span"
          size="body-3"
          role="button"
          color="blue-500"
          onClick={handleClickResendButton}
        >
          이메일을 받지 못하셨나요?
        </Typography>
        <Button
          color="blue"
          variant="filled"
          aria-label="기재해준 이메일을 통해 인증 후, 비밀번호 설정 페이지로 이동합니다."
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

export default JoinEmailVerifyBox;
