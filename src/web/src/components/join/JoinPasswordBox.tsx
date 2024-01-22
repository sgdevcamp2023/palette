import { useState, type ChangeEvent } from 'react';

import { StepTitle } from '..';
import { Button, Input, Typography } from '../common';
import type { NavigationEvent, JoinInfo } from './joinReducer';

interface JoinPasswordBoxProps extends NavigationEvent {
  password: string;
  disabled: boolean;
  onChangeInput: (
    e: ChangeEvent<HTMLInputElement>,
    type: keyof JoinInfo,
  ) => void;
}

function JoinPasswordBox({
  password,
  disabled,
  onNextStep,
  onChangeInput,
}: JoinPasswordBoxProps) {
  const [isHidden, setIsHidden] = useState<boolean>(true);
  const [isDirty, setIsDirty] = useState<boolean>(false);

  return (
    <>
      <StepTitle
        title="비밀번호가 필요합니다"
        description="8자 이상이어야 합니다."
        className="mt-[28px]"
      />
      <div className="h-full mt-[50px] flex flex-col gap-[40px]">
        <Input
          label="비밀번호"
          value={password}
          type={isHidden ? 'password' : 'text'}
          icon={{
            type: isHidden ? 'eyeOff' : 'eyeOn',
            stroke: 'blueGrey-400',
            onClick: () => setIsHidden((prev) => !prev),
          }}
          status={isDirty ? 'dirty' : 'normal'}
          onFocus={() => setIsDirty(true)}
          onBlur={() => setIsDirty(false)}
          onChange={(e) => onChangeInput(e, 'password')}
        />
      </div>
      <Button
        color="blue"
        variant="filled"
        aria-label="프로필 이미지 선택 페이지로 이동합니다."
        disabled={disabled}
        aria-disabled={disabled}
        onClick={onNextStep}
      >
        <Typography size="body-2" color="white">
          다음
        </Typography>
      </Button>
    </>
  );
}

export default JoinPasswordBox;
