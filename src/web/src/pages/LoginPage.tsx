import { toast } from 'react-toastify';
import type { ChangeEvent } from 'react';
import { useReducer, useState } from 'react';
import { useNavigate } from '@tanstack/react-router';
import { useMutation } from '@tanstack/react-query';
import { Helmet, HelmetProvider } from 'react-helmet-async';

import { apis } from '@/api';
import type { LoginInfo } from '@/@types';
import { ContentLayout, Header } from '@/components';
import { LoginStep, LoginStepReducer } from '@/components/login/loginReducer';
import { LoginEmailBox, LoginPasswordBox } from '@/components/login';
import {
  accessTokenStorage,
  refreshTokenStorage,
} from '@/api/AuthTokenStorage';

function LoginPage() {
  const [state, dispatch] = useReducer(LoginStepReducer, LoginStep.EMAIL);
  const [loginInfo, setLoginInfo] = useState<LoginInfo>({
    email: '',
    password: '',
  });
  const navigate = useNavigate();
  const loginMutate = useMutation({
    mutationKey: ['login', loginInfo.email],
    mutationFn: () => apis.auth.login(loginInfo),
    onSuccess: (res) => {
      toast(`${loginInfo.email}님 로그인이 완료되었습니다.`);
      accessTokenStorage.setToken(res.accessToken);
      refreshTokenStorage.setToken(res.refreshToken);
      navigate({ to: '/home' });
    },
    onError: () => toast.error('아이디 혹은 비밀번호가 틀렸습니다.'),
  });

  const handleChangeInput = (
    e: ChangeEvent<HTMLInputElement>,
    type: keyof LoginInfo,
  ) => {
    setLoginInfo((prev) => ({ ...prev, [type]: e.target.value }));
  };

  const onNextPage = () => dispatch({ direction: 'next' });
  const onPrevPage = () => {
    if (state === LoginStep.EMAIL) {
      navigate({ to: '/' });
      return;
    }
    dispatch({ direction: 'prev' });
  };

  const getLoginBox = (step: LoginStep): JSX.Element => {
    switch (step) {
      case LoginStep.EMAIL:
        return (
          <LoginEmailBox
            disabled={loginInfo.email === ''}
            email={loginInfo.email}
            onNextStep={onNextPage}
            onChangeInput={handleChangeInput}
            onClickForgetPassword={() => navigate({ to: '/change-password' })}
          />
        );
      case LoginStep.PASSWORD:
        return (
          <LoginPasswordBox
            disabled={loginInfo.password === ''}
            email={loginInfo.email}
            password={loginInfo.password}
            onLogin={() => loginMutate.mutate()}
            onChangeInput={handleChangeInput}
            onClickForgetPassword={() => navigate({ to: '/change-password' })}
          />
        );
      default:
        return (
          <LoginEmailBox
            disabled={loginInfo.email === ''}
            email={loginInfo.email}
            onNextStep={onNextPage}
            onChangeInput={handleChangeInput}
            onClickForgetPassword={() => navigate({ to: '/change-password' })}
          />
        );
    }
  };

  const children = getLoginBox(state);

  return (
    <>
      <HelmetProvider>
        <Helmet>
          <title>Easel | 로그인</title>
          <meta name="description" content="로그인 페이지" />
        </Helmet>
      </HelmetProvider>
      <Header
        left={{
          type: state === LoginStep.EMAIL ? 'text' : 'leftStickArrow',
          label: state === LoginStep.EMAIL ? '취소' : '뒤로가기',
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
        <div className="flex flex-col h-full pb-[24px] overflow-hidden">
          {children}
        </div>
      </ContentLayout>
    </>
  );
}

export default LoginPage;
