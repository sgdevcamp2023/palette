import { memo } from 'react';

import { cn } from '@/utils';
import { Typography } from './common';

interface NotSupportBoxProps {
  className?: string;
  description?: string;
}

function NotSupportBox({ className, description }: NotSupportBoxProps) {
  return (
    <div
      className={cn(
        'flex flex-col gap-[40px] h-full justify-center items-center px-4',
        className,
      )}
    >
      <div className="flex items-center justify-center w-[200px] h-[200px] rounded-full bg-yellow-100">
        <Typography size="headline-1" className="text-[100px]">
          😅
        </Typography>
      </div>
      <div className="w-full flex flex-col justify-center items-center gap-3">
        <Typography size="headline-7" color="grey-600">
          아직 지원하지 않는 기능입니다.
        </Typography>
        <Typography size="body-1" color="grey-600">
          {description}
        </Typography>
      </div>
    </div>
  );
}

const MemoizedNotSupportBox = memo(NotSupportBox);

export default MemoizedNotSupportBox;
