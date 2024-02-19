import { toast } from 'react-toastify';
import type { ChangeEvent } from 'react';
import { useReducer, useState } from 'react';
import { useNavigate } from '@tanstack/react-router';
import { useMutation } from '@tanstack/react-query';
import { Helmet, HelmetProvider } from 'react-helmet-async';

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
import { apis } from '@/api';

const MAX_PASSWORD_LENGTH = 8;

function JoinPage() {
  const [state, dispatch] = useReducer(joinStepReducer, JoinStep.INFORMATION);
  const [joinInfo, setJoinInfo] = useState<JoinInfo>({
    nickname: '',
    username: '',
    email: '',
    emailVerifyCode: '',
    password: '',
    profileImagePath: '',
  });
  const navigate = useNavigate();

  const registerMutate = useMutation({
    mutationKey: ['register', joinInfo.username],
    mutationFn: () =>
      apis.users.join({
        email: joinInfo.email,
        password: joinInfo.password,
        username: joinInfo.username,
        profileImagePath: joinInfo.profileImagePath,
      }),
    onSuccess: () => {
      toast(`${joinInfo.username}님 회원가입이 완료되었습니다.`);
      navigate({ to: '/' });
    },
    onError: () => {
      toast('회원가입에 문제가 생겼습니다.');
    },
  });

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
            email={joinInfo.email}
            nickname={joinInfo.nickname}
            onNextStep={onNextPage}
            onChangeInput={handleChangeInput}
          />
        );
      case JoinStep.EMAIL_VERIFY:
        return (
          <JoinEmailVerifyBox
            email={joinInfo.email}
            emailVerifyCode={joinInfo.emailVerifyCode}
            disabled={joinInfo.emailVerifyCode === ''}
            onNextStep={onNextPage}
            onChangeInput={handleChangeInput}
          />
        );
      case JoinStep.PASSWORD:
        return (
          <JoinPasswordBox
            password={joinInfo.password}
            disabled={joinInfo.password.length < MAX_PASSWORD_LENGTH}
            onNextStep={onNextPage}
            onChangeInput={handleChangeInput}
          />
        );
      case JoinStep.PROFILE_IMAGE:
        return (
          <JoinProfileImageBox
            disabled={joinInfo.profileImagePath === ''}
            imageSrc={joinInfo.profileImagePath}
            onNextStep={onNextPage}
            onChangeImage={(path: string) =>
              setJoinInfo((prev) => ({ ...prev, profileImagePath: path }))
            }
            onJoin={() => registerMutate.mutate()}
          />
        );
      case JoinStep.NAME:
        return (
          <JoinNameBox
            username={joinInfo.username}
            disabled={joinInfo.username === ''}
            onJoin={() => registerMutate.mutate()}
            onChangeInput={handleChangeInput}
          />
        );
      default:
        return (
          <JoinEmailBox
            email={joinInfo.email}
            nickname={joinInfo.nickname}
            onNextStep={onNextPage}
            onChangeInput={handleChangeInput}
          />
        );
    }
  };

  const children = getJoinBox(state);

  return (
    <>
      <HelmetProvider>
        <Helmet>
          <title>Easel | 회원가입</title>
          <meta name="description" content="회원가입 페이지" />
        </Helmet>
      </HelmetProvider>
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
      <ContentLayout
        isShowBottomNavigation={false}
        className="h-full"
        isShowFloatingButton={false}
      >
        <div className="flex flex-col h-full pb-[24px] justify-between overflow-hidden">
          {children}
        </div>
      </ContentLayout>
    </>
  );
}

export default JoinPage;
