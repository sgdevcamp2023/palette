import { toast } from 'react-toastify';
import type { ChangeEvent } from 'react';
import { useReducer, useState } from 'react';
import { useNavigate } from '@tanstack/react-router';

import type { LoginInfo } from '@/@types';
import { ContentLayout, Header } from '@/components';
import { LoginStep, LoginStepReducer } from '@/components/login/loginReducer';
import { LoginEmailBox, LoginPasswordBox } from '@/components/login';

function LoginPage() {
  const [state, dispatch] = useReducer(LoginStepReducer, LoginStep.EMAIL);
  const [loginInfo, setLoginInfo] = useState<LoginInfo>({
    email: '',
    password: '',
  });
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      // TODO: api 연동
      toast(`${loginInfo.email}님 로그인이 완료되었습니다.`);
      navigate({ to: '/home' });
    } catch (err) {
      toast.error('서버에 잠시 문제가 생겼습니다.');
    }
  };

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
            onLogin={handleLogin}
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
      <ContentLayout isShowBottomNavigation={false} className="h-full">
        <div className="flex flex-col h-full pb-[24px] overflow-hidden">
          {children}
        </div>
      </ContentLayout>
    </>
  );
}

export default LoginPage;
