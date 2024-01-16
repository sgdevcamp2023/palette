import type { ChangeEvent } from 'react';

import { StepTitle } from '..';
import type { SignUpInfo } from './signUpReducer';
import { Button, Input, Typography } from '../common';

interface SignUpNameBoxProps {
  username: string;
  disabled?: boolean;
  onChangeInput: (
    e: ChangeEvent<HTMLInputElement>,
    type: keyof SignUpInfo,
  ) => void;
  onSignUp: VoidFunction;
}

function SignUpNameBox({
  username,
  disabled,
  onSignUp,
  onChangeInput,
}: SignUpNameBoxProps) {
  return (
    <>
      <StepTitle
        title="이름을 가르쳐 주시곘어요?"
        description="@사용자 아이디는 고유한 나만의 아이디입니다. 나중에 언제든 바꿀 수 있습니다."
        className="mt-[28px]"
      />
      <div className="h-full mt-[50px] flex flex-col gap-[40px]">
        <Input
          value={username}
          label="사용자 아이디"
          className="[&_input]:text-blue-500"
          onChange={(e) => onChangeInput(e, 'username')}
        />
      </div>

      <Button
        color="black"
        variant="filled"
        aria-label="회원가입을 진행합니다."
        disabled={disabled}
        aria-disabled={disabled}
        onClick={onSignUp}
      >
        <Typography size="body-2" color="white">
          가입
        </Typography>
      </Button>
    </>
  );
}

export default SignUpNameBox;
