import type { FallbackProps } from 'react-error-boundary';

import { AsyncBoundary, Button, Typography } from '@/components';

function ErrorFallbackPage({ error }: FallbackProps) {
  return (
    <AsyncBoundary>
      <div className="flex flex-col h-full justify-evenly items-center px-4">
        <div className="flex items-center justify-center w-[200px] h-[200px] rounded-full bg-yellow-100">
          <Typography size="headline-1" className="text-[100px]">
            😱
          </Typography>
        </div>
        <div className="w-full flex flex-col justify-center items-center gap-3">
          <Typography size="headline-7" color="grey-600">
            잘못된 접근입니다.
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
    </AsyncBoundary>
  );
}

export default ErrorFallbackPage;
