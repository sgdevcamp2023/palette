import { toast } from 'react-toastify';
import type { ChangeEvent } from 'react';
import { useReducer, useState } from 'react';
import { useNavigate } from '@tanstack/react-router';

import { Header, ContentLayout } from '@/components';
import type { SignUpInfo } from '@/components/signup/signUpReducer';
import {
  SignUpStep,
  SignUpStepReducer,
} from '@/components/signup/signUpReducer';
import {
  SignUpEmailBox,
  SignUpEmailVerifyBox,
  SignUpNameBox,
  SignUpPasswordBox,
  SignUpProfileImageBox,
} from '@/components/signup';

const DUMMY_VERIFIED = 'abc123';

function SignUpPage() {
  const [state, dispatch] = useReducer(
    SignUpStepReducer,
    SignUpStep.INFORMATION,
  );
  const [signUpInfo, setSignUpInfo] = useState<SignUpInfo>({
    nickname: '',
    username: '',
    email: '',
    emailVerifyCode: '',
    password: '',
    profilePath: '',
  });
  const navigate = useNavigate();

  const handleSignUp = async () => {
    try {
      // TODO: api 연동
      toast(`${signUpInfo.username}님 회원가입이 완료되었습니다.`);
      navigate({ to: '/' });
    } catch (err) {
      toast.error('서버에 잠시 문제가 생겼습니다.');
    }
  };

  const onNextPage = () => dispatch({ direction: 'next' });
  const onPrevPage = () => {
    if (state === SignUpStep.INFORMATION) {
      navigate({ to: '/entry' });
      return;
    }
    dispatch({ direction: 'prev' });
  };

  const handleChangeInput = (
    e: ChangeEvent<HTMLInputElement>,
    type: keyof SignUpInfo,
  ) => {
    setSignUpInfo((prev) => ({ ...prev, [type]: e.target.value }));
  };

  const getSignUpBox = (step: SignUpStep): JSX.Element => {
    switch (step) {
      case SignUpStep.INFORMATION:
        return (
          <SignUpEmailBox
            disabled={signUpInfo.email === '' || signUpInfo.nickname === ''}
            email={signUpInfo.email}
            nickname={signUpInfo.nickname}
            onNextStep={onNextPage}
            onChangeInput={handleChangeInput}
          />
        );
      case SignUpStep.EMAIL_VERIFY:
        return (
          <SignUpEmailVerifyBox
            email={signUpInfo.email}
            emailVerifyCode={signUpInfo.emailVerifyCode}
            disabled={signUpInfo.emailVerifyCode !== DUMMY_VERIFIED}
            onNextStep={onNextPage}
            onChangeInput={handleChangeInput}
          />
        );
      case SignUpStep.PASSWORD:
        return (
          <SignUpPasswordBox
            password={signUpInfo.password}
            disabled={signUpInfo.password.length < 8}
            onNextStep={onNextPage}
            onChangeInput={handleChangeInput}
          />
        );
      case SignUpStep.PROFILE_IMAGE:
        return (
          <SignUpProfileImageBox
            disabled={signUpInfo.profilePath === ''}
            onNextStep={onNextPage}
            onChangeImage={(path: string) =>
              setSignUpInfo((prev) => ({ ...prev, profilePath: path }))
            }
            onSignUp={handleSignUp}
          />
        );
      case SignUpStep.NAME:
        return (
          <SignUpNameBox
            username={signUpInfo.username}
            disabled={signUpInfo.username === ''}
            onSignUp={handleSignUp}
            onChangeInput={handleChangeInput}
          />
        );
      default:
        return (
          <SignUpEmailBox
            email={signUpInfo.email}
            nickname={signUpInfo.nickname}
            onNextStep={onNextPage}
            onChangeInput={handleChangeInput}
          />
        );
    }
  };

  const children = getSignUpBox(state);

  return (
    <>
      <Header
        left={{
          type: state === SignUpStep.INFORMATION ? 'text' : 'leftStickArrow',
          label: state === SignUpStep.INFORMATION ? '취소' : '뒤로가기',
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

export default SignUpPage;
