import { memo, useCallback } from 'react';
import type { FormEvent, ChangeEvent } from 'react';

import { StepTitle } from '..';
import type { JoinInfo } from './joinReducer';
import { Button, Input, Typography } from '../common';

interface JoinNameBoxProps {
  username: string;
  disabled?: boolean;
  onChangeInput: (
    e: ChangeEvent<HTMLInputElement>,
    type: keyof JoinInfo,
  ) => void;
  onJoin: VoidFunction;
}

function JoinNameBox({
  username,
  disabled,
  onJoin,
  onChangeInput,
}: JoinNameBoxProps) {
  const handleSubmitForm = useCallback((e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    onJoin();
  }, []);

  return (
    <>
      <StepTitle
        title="이름을 가르쳐 주시곘어요?"
        description="@사용자 아이디는 고유한 나만의 아이디입니다. 나중에 언제든 바꿀 수 있습니다."
        className="mt-[28px]"
      />
      <form onSubmit={handleSubmitForm} className="w-full h-full">
        <div className="flex flex-col h-full pb-[24px] justify-between overflow-hidden">
          <div className="h-full mt-[50px] flex flex-col gap-[40px]">
            <Input
              value={username}
              label="사용자 아이디"
              className="[&_input]:text-blue-500"
              onChange={(e) => onChangeInput(e, 'username')}
            />
          </div>

          <Button
            type="submit"
            color="black"
            variant="filled"
            disabled={disabled}
            aria-disabled={disabled}
            onClick={onJoin}
          >
            <Typography size="body-2" color="white">
              가입
            </Typography>
          </Button>
        </div>
      </form>
    </>
  );
}

const MemoizedJoinNameBox = memo(JoinNameBox);

export default MemoizedJoinNameBox;
