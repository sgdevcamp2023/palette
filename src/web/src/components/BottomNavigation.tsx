import type { RefObject } from 'react';
import { motion } from 'framer-motion';
import { useEffect, useState } from 'react';
import { cva } from 'class-variance-authority';
import { useNavigate, useMatchRoute } from '@tanstack/react-router';

import { useThrottle } from '@/hooks';
import AccessibleIconButton from './AccessibleIconButton';

const BottomNavigationVariants = cva<{
  direction: Record<'up' | 'down' | 'stop', string>;
}>(
  'w-[420px] max-w-full h-[50px] leading-[50px] px-[28px] fixed bottom-0 flex justify-between border-t-[1px] bg-white transition-opacity',
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

interface BottomNavigationProps {
  contentRef: RefObject<HTMLElement> | null;
}

const FramerAccessibleIconButton = motion(AccessibleIconButton);

const iconOpacity = (direction: 'up' | 'down' | 'stop') => {
  if (direction === 'up') return 'opacity-95';
  if (direction === 'down') return 'opacity-80';
  return '';
};

function BottomNavigation({ contentRef }: BottomNavigationProps) {
  if (!contentRef) return null;
  const navigate = useNavigate();
  const matchRoute = useMatchRoute();
  const [y, setY] = useState<number>(0);
  const [scrollDirection, setScrollDirection] = useState<
    'stop' | 'up' | 'down'
  >('stop');

  /**
   * 아래로 드래그가 되고 있다면 BottomNavigation의 투명도를 주어 컨텐츠를 방해하지 않습니다.
   * 위로 드래그가 되고 있다면 투명도를 제거합니다.
   *
   * 최적화를 위해 throttle을 사용합니다.
   */
  const onScroll = useThrottle((e: Event) => {
    const padding = 50;
    const { scrollTop } = e.target as HTMLElement;

    if (y > scrollTop + padding) {
      setScrollDirection('up');
    } else if (y < scrollTop - padding) {
      setScrollDirection('down');
    }
    setY(scrollTop);
  }, 500);

  useEffect(() => {
    contentRef.current?.addEventListener('scroll', onScroll);
    return () => {
      contentRef.current?.removeEventListener('scroll', onScroll);
    };
  }, [contentRef.current]);

  return (
    <nav className={BottomNavigationVariants({ direction: scrollDirection })}>
      <FramerAccessibleIconButton
        role="navigation"
        iconType="home"
        width={22}
        label="홈 화면으로 이동"
        disabled={!!matchRoute({ to: '/home' })}
        onClick={() => navigate({ to: '/home' })}
        className={iconOpacity(scrollDirection)}
        fill={matchRoute({ to: '/home' }) ? 'black' : undefined}
        whileHover={{
          scale: 1.1,
        }}
        whileTap={{ scale: 0.8 }}
      />
      <FramerAccessibleIconButton
        role="navigation"
        iconType="search"
        width={20}
        label="통합 검색 화면으로 이동"
        disabled={!!matchRoute({ to: '/search' })}
        onClick={() => navigate({ to: '/search' })}
        stroke={matchRoute({ to: '/search' }) ? 'black' : undefined}
        className={iconOpacity(scrollDirection)}
        whileHover={{
          scale: 1.1,
        }}
        whileTap={{ scale: 0.8 }}
      />
      <FramerAccessibleIconButton
        role="navigation"
        iconType={matchRoute({ to: '/notification' }) ? 'solidBell' : 'bell'}
        width={20}
        label="알림 화면으로 이동"
        disabled={!!matchRoute({ to: '/notification' })}
        onClick={() => navigate({ to: '/notification' })}
        fill={matchRoute({ to: '/notification' }) ? 'black' : undefined}
        className={iconOpacity(scrollDirection)}
        whileHover={{
          scale: 1.1,
        }}
        whileTap={{ scale: 0.8 }}
      />
      <FramerAccessibleIconButton
        role="navigation"
        iconType={matchRoute({ to: '/chat' }) ? 'solidMail' : 'mail'}
        width={20}
        label="채팅 화면으로 이동"
        disabled={!!matchRoute({ to: '/chat' })}
        onClick={() => navigate({ to: '/chat' })}
        className={iconOpacity(scrollDirection)}
        whileHover={{
          scale: 1.1,
        }}
        whileTap={{ scale: 0.8 }}
      />
    </nav>
  );
}

export default BottomNavigation;
