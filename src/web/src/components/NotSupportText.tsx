import { memo } from 'react';
import { toast } from 'react-toastify';

import { cn } from '@/utils';
import Typography from './common/Typography';
import type { ColorType, FontKeyType } from '@/@types';

interface NotSupportTextProps {
  text: string;
  className?: string;
  size?: FontKeyType;
  color?: ColorType;
}

function NotSupportText({
  text,
  className,
  size = 'body-3',
  color = 'blue-500',
}: NotSupportTextProps) {
  return (
    <Typography
      as="span"
      role="alertdialog"
      size={size}
      color={color}
      className={cn('mx-1 cursor-pointer', className)}
      onClick={() => toast('아직 지원되지 않는 기능입니다.')}
    >
      {text}
    </Typography>
  );
}

const MemoizedNotSupportText = memo(NotSupportText);

export default MemoizedNotSupportText;
