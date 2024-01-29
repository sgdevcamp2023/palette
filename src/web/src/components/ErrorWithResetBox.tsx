import type { FallbackProps } from 'react-error-boundary';

import Button from './common/Button';
import Typography from './common/Typography';

function ErrorWithResetBox({ error, resetErrorBoundary }: FallbackProps) {
  return (
    <div className="flex flex-col gap-[40px] h-full justify-center items-center px-4">
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
          메시지:
          {error instanceof Error ? error.message : '잘못된 요청입니다.'}
        </Typography>
      </div>
      <Button role="navigation" onClick={resetErrorBoundary}>
        <Typography size="body-2" color="white">
          재시도 하기
        </Typography>
      </Button>
    </div>
  );
}

export default ErrorWithResetBox;
