import Lottie from 'react-lottie';
import { toast } from 'react-toastify';
import { useNavigate } from '@tanstack/react-router';
import { Helmet, HelmetProvider } from 'react-helmet-async';

import earthLottie from '@/components/common/lottie/earth.json';
import { Button, Typography, ContentLayout } from '@/components';

function MembershipEntryPage() {
  const navigate = useNavigate();

  return (
    <>
      <HelmetProvider>
        <Helmet>
          <title>Easel | 홈</title>
          <meta name="description" content="홈 페이지" />
        </Helmet>
      </HelmetProvider>
      <ContentLayout
        isShowBottomNavigation={false}
        className="h-full"
        isShowFloatingButton={false}
      >
        <div className="flex flex-col h-full pb-[50px] justify-between overflow-hidden">
          <Typography size="headline-1" color="grey-600" className="mt-[28px]">
            지금 세계에서 무슨 일이 일어나고 있는지 알아보세요.
          </Typography>
          <div className="min-h-[260px] self-center">
            <Lottie
              width={260}
              height={260}
              options={{
                loop: true,
                autoplay: true,
                animationData: earthLottie,
                rendererSettings: {
                  preserveAspectRatio: 'xMidYMid slice',
                },
              }}
            />
          </div>
          <Button
            role="navigation"
            variant="filled"
            onClick={() => navigate({ to: '/join' })}
          >
            <Typography size="sub-headline-2" color="white">
              계정 만들기
            </Typography>
          </Button>
          <div className="flex flex-col gap-[20px]">
            <Typography
              as="span"
              size="body-3"
              color="blueGrey-800"
              className="mt-[26px]"
            >
              가입하면 이젤의
              <Typography
                as="span"
                role="alertdialog"
                size="body-3"
                color="blue-500"
                className="mx-1 cursor-pointer"
                onClick={() => toast('아직 지원되지 않는 기능입니다.')}
              >
                이용 약관, 개인정보 처리방침, 쿠키 사용
              </Typography>
              에 동의하게 됩니다.
            </Typography>

            <Typography as="span" size="body-2" color="blueGrey-800">
              이미 계정이 있으세요?
              <Typography
                as="button"
                role="navigation"
                size="body-2"
                color="blue-500"
                className="mx-1"
                onClick={() => navigate({ to: '/login' })}
              >
                로그인하기
              </Typography>
            </Typography>
          </div>
        </div>
      </ContentLayout>
    </>
  );
}

export default MembershipEntryPage;
