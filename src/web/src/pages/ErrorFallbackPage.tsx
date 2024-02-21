import { Helmet, HelmetProvider } from 'react-helmet-async';
import type { FallbackProps } from 'react-error-boundary';

import { Button, Typography } from '@/components';

function ErrorFallbackPage({ error }: FallbackProps) {
  return (
    <>
      <HelmetProvider>
        <Helmet>
          <title>Easel | 오류</title>
          <meta name="description" content="오륲 페이지" />
        </Helmet>
      </HelmetProvider>
      <div className="flex flex-col h-full justify-evenly items-center px-4">
        <div className="flex items-center justify-center w-[200px] h-[200px] rounded-full bg-yellow-100">
          <Typography size="headline-1" className="text-[100px]">
            😱
          </Typography>
        </div>
        <div className="w-full flex flex-col justify-center items-center gap-3">
          <Typography size="headline-7" color="grey-600">
            오류가 발생했습니다.
          </Typography>
          <Typography size="body-1" color="grey-600">
            메시지:{' '}
            {error instanceof Error ? error.message : '잘못된 페이지입니다.'}
          </Typography>
        </div>
        <Button
          role="navigation"
          onClick={() => {
            window.location.href = '/home';
          }}
        >
          <Typography size="body-2" color="white">
            홈 화면으로 돌아가기
          </Typography>
        </Button>
      </div>
    </>
  );
}

export default ErrorFallbackPage;
