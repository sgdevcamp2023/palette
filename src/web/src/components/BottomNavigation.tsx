import { memo } from 'react';
import { cva } from 'class-variance-authority';
import { useNavigate, useMatchRoute } from '@tanstack/react-router';

import { iconOpacity } from '@/utils';
import type { ScrollDirectionProps } from '@/@types';
import { FramerAccessibleIconButton } from './AccessibleIconButton';

const BottomNavigationVariants = cva<{
  direction: Record<'up' | 'down' | 'stop', string>;
}>(
  'w-[420px] max-w-full h-[50px] leading-[50px] px-[28px] fixed bottom-0 flex justify-between border-t-[1px] bg-white transition-opacity z-[9995]',
  {
    variants: {
      direction: {
        up: 'border-grey-300 opacity-95',
        down: 'opacity-80',
        stop: '',
      },
    },
    defaultVariants: {
      direction: 'stop',
    },
  },
);

function BottomNavigation({ direction }: ScrollDirectionProps) {
  const navigate = useNavigate();
  const matchRoute = useMatchRoute();

  return (
    <nav className={BottomNavigationVariants({ direction })}>
      <FramerAccessibleIconButton
        iconType="home"
        width={22}
        label="홈 화면으로 이동"
        disabled={!!matchRoute({ to: '/home' })}
        onClick={() => navigate({ to: '/home' })}
        className={iconOpacity(direction)}
        fill={matchRoute({ to: '/home' }) ? 'black' : undefined}
        whileHover={{
          scale: 1.1,
        }}
        whileTap={{ scale: 0.8 }}
      />
      <FramerAccessibleIconButton
        iconType="search"
        width={20}
        label="통합 검색 화면으로 이동"
        disabled={!!matchRoute({ to: '/search' })}
        onClick={() => navigate({ to: '/search' })}
        stroke={matchRoute({ to: '/search' }) ? 'black' : undefined}
        className={iconOpacity(direction)}
        whileHover={{
          scale: 1.1,
        }}
        whileTap={{ scale: 0.8 }}
      />
      <FramerAccessibleIconButton
        iconType={matchRoute({ to: '/notification' }) ? 'solidBell' : 'bell'}
        width={20}
        label="알림 화면으로 이동"
        disabled={!!matchRoute({ to: '/notification' })}
        onClick={() => navigate({ to: '/notification' })}
        fill={matchRoute({ to: '/notification' }) ? 'black' : undefined}
        className={iconOpacity(direction)}
        whileHover={{
          scale: 1.1,
        }}
        whileTap={{ scale: 0.8 }}
      />
      <FramerAccessibleIconButton
        iconType={matchRoute({ to: '/chat' }) ? 'solidMail' : 'mail'}
        width={20}
        label="채팅 화면으로 이동"
        disabled={!!matchRoute({ to: '/chat' })}
        onClick={() => navigate({ to: '/chat' })}
        className={iconOpacity(direction)}
        whileHover={{
          scale: 1.1,
        }}
        whileTap={{ scale: 0.8 }}
      />
    </nav>
  );
}

const MemoizedBottomNavigation = memo(BottomNavigation);

export default MemoizedBottomNavigation;
