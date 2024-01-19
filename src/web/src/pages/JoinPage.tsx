import { toast } from 'react-toastify';
import type { ChangeEvent } from 'react';
import { useReducer, useState } from 'react';
import { useNavigate } from '@tanstack/react-router';

import { Header, ContentLayout } from '@/components';
import type { JoinInfo } from '@/components/join/joinReducer';
import { JoinStep, joinStepReducer } from '@/components/join/joinReducer';
import {
  JoinEmailBox,
  JoinEmailVerifyBox,
  JoinNameBox,
  JoinPasswordBox,
  JoinProfileImageBox,
} from '@/components/join';

const DUMMY_VERIFIED = 'abc123';

function JoinPage() {
  const [state, dispatch] = useReducer(joinStepReducer, JoinStep.INFORMATION);
  const [JoinInfo, setJoinInfo] = useState<JoinInfo>({
    nickname: '',
    username: '',
    email: '',
    emailVerifyCode: '',
    password: '',
    profilePath: '',
  });
  const navigate = useNavigate();

  const handleJoin = async () => {
    try {
      // TODO: api 연동
      toast(`${JoinInfo.username}님 회원가입이 완료되었습니다.`);
      navigate({ to: '/' });
    } catch (err) {
      toast.error('서버에 잠시 문제가 생겼습니다.');
    }
  };

  const onNextPage = () => dispatch({ direction: 'next' });
  const onPrevPage = () => {
    if (state === JoinStep.INFORMATION) {
      navigate({ to: '/' });
      return;
    }
    dispatch({ direction: 'prev' });
  };

  const handleChangeInput = (
    e: ChangeEvent<HTMLInputElement>,
    type: keyof JoinInfo,
  ) => {
    setJoinInfo((prev) => ({ ...prev, [type]: e.target.value }));
  };

  const getJoinBox = (step: JoinStep): JSX.Element => {
    switch (step) {
      case JoinStep.INFORMATION:
        return (
          <JoinEmailBox
            disabled={JoinInfo.email === '' || JoinInfo.nickname === ''}
            email={JoinInfo.email}
            nickname={JoinInfo.nickname}
            onNextStep={onNextPage}
            onChangeInput={handleChangeInput}
          />
        );
      case JoinStep.EMAIL_VERIFY:
        return (
          <JoinEmailVerifyBox
            email={JoinInfo.email}
            emailVerifyCode={JoinInfo.emailVerifyCode}
            disabled={JoinInfo.emailVerifyCode !== DUMMY_VERIFIED}
            onNextStep={onNextPage}
            onChangeInput={handleChangeInput}
          />
        );
      case JoinStep.PASSWORD:
        return (
          <JoinPasswordBox
            password={JoinInfo.password}
            disabled={JoinInfo.password.length < 8}
            onNextStep={onNextPage}
            onChangeInput={handleChangeInput}
          />
        );
      case JoinStep.PROFILE_IMAGE:
        return (
          <JoinProfileImageBox
            disabled={JoinInfo.profilePath === ''}
            onNextStep={onNextPage}
            onChangeImage={(path: string) =>
              setJoinInfo((prev) => ({ ...prev, profilePath: path }))
            }
            onJoin={handleJoin}
          />
        );
      case JoinStep.NAME:
        return (
          <JoinNameBox
            username={JoinInfo.username}
            disabled={JoinInfo.username === ''}
            onJoin={handleJoin}
            onChangeInput={handleChangeInput}
          />
        );
      default:
        return (
          <JoinEmailBox
            email={JoinInfo.email}
            nickname={JoinInfo.nickname}
            onNextStep={onNextPage}
            onChangeInput={handleChangeInput}
          />
        );
    }
  };

  const children = getJoinBox(state);

  return (
    <>
      <Header
        left={{
          type: state === JoinStep.INFORMATION ? 'text' : 'leftStickArrow',
          label: state === JoinStep.INFORMATION ? '취소' : '뒤로가기',
          onClick: onPrevPage,
        }}
        center={{
          type: 'palette',
          label: '서비스 로고',
          width: 26,
          height: 26,
        }}
      />
      <ContentLayout isShowBottomNavigation={false} className="h-full">
        <div className="flex flex-col h-full pb-[24px] justify-between overflow-hidden">
          {children}
        </div>
      </ContentLayout>
    </>
  );
}

export default JoinPage;