import { memo, useEffect, useRef, useState } from 'react';
import type { ElementType, HTMLAttributes, ReactNode } from 'react';

import FloatingButton from './FloatingButton';
import BottomNavigation from './BottomNavigation';
import { cn } from '@/utils';
import { useThrottle } from '@/hooks';

interface ContentLayoutProps extends HTMLAttributes<HTMLElement> {
  as?: ElementType;
  children: ReactNode;
  className?: string;
  isShowFloatingButton?: boolean;
  isShowBottomNavigation?: boolean;
}

function ContentLayout({
  children,
  className,
  as,
  isShowBottomNavigation = true,
  isShowFloatingButton = true,
  ...props
}: ContentLayoutProps) {
  const Component = as || 'main';
  const ref = useRef<HTMLElement | null>(null);
  const [y, setY] = useState<number>(0);
  const [scrollDirection, setScrollDirection] = useState<
    'stop' | 'up' | 'down'
  >('stop');

  /**
   * 아래로 드래그가 되고 있다면 BottomNavigation, FloatingButton에 투명도를 주어 컨텐츠를 방해하지 않습니다.
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
  }, 200);

  useEffect(() => {
    ref.current?.addEventListener('scroll', onScroll);
    return () => {
      ref.current?.removeEventListener('scroll', onScroll);
    };
  }, [ref.current]);

  return (
    <>
      <Component
        className={cn(
          'px-[24px] mt-[44px] mb-[50px] overflow-y-scroll max-h-[calc(100vh-44px)] overscroll-contain',
          className,
        )}
        ref={ref}
        {...props}
      >
        {children}
      </Component>
      {isShowBottomNavigation && (
        <footer className="w-[420px] h-[50px]">
          <BottomNavigation direction={scrollDirection} />
        </footer>
      )}
      {isShowFloatingButton && <FloatingButton direction={scrollDirection} />}
    </>
  );
}

const MemoizedContentLayout = memo(ContentLayout);

export default MemoizedContentLayout;
