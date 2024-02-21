import { useState } from 'react';
import { toast } from 'react-toastify';
import { useNavigate } from '@tanstack/react-router';
import { Helmet, HelmetProvider } from 'react-helmet-async';

import {
  Button,
  ContentLayout,
  Header,
  Input,
  StepTitle,
  Typography,
} from '@/components';

function ChangePasswordPage() {
  const [email, setEmail] = useState<string>('');
  const navigate = useNavigate();

  const handleClickConfirm = () => {
    toast('임시 비밀번호를 사용하신 이메일로 발송했습니다.');
    navigate({ to: '/' });
  };

  return (
    <>
      <HelmetProvider>
        <Helmet>
          <title>Easel | 비밀번호 변경</title>
          <meta name="description" content="비밀번호 변경 페이지" />
        </Helmet>
      </HelmetProvider>
      <Header
        left={{
          type: 'text',
          label: '취소',
          onClick: () => navigate({ to: '../' }),
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
        <div className="flex flex-col gap-[54px]">
          <StepTitle
            title="내 Easel 계정 찾기"
            description="비밀번호를 변경하려면 계정에 연결된 이메일 또는 사용자 아이디를 입력해 주세요."
            className="mt-[28px]"
          />
          <Input
            label="이메일 또는 사용자 아이디"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>
        <Button
          className="mt-[100px]"
          disabled={email === ''}
          aria-disabled={email === ''}
          onClick={handleClickConfirm}
        >
          <Typography size="body-2" color="white">
            확인
          </Typography>
        </Button>
      </ContentLayout>
    </>
  );
}

export default ChangePasswordPage;
