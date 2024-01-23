import { useRef } from 'react';
import type { ElementType, HTMLAttributes, ReactNode } from 'react';

import BottomNavigation from '../BottomNavigation';
import { cn } from '@/utils';

interface ContentLayoutProps extends HTMLAttributes<HTMLElement> {
  as?: ElementType;
  children: ReactNode;
  className?: string;
  isShowBottomNavigation?: boolean;
}

function ContentLayout({
  children,
  className,
  as,
  isShowBottomNavigation = true,
  ...props
}: ContentLayoutProps) {
  const Component = as || 'main';
  const ref = useRef<HTMLElement | null>(null);

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
          <BottomNavigation contentRef={ref} />
        </footer>
      )}
    </>
  );
}

export default ContentLayout;
