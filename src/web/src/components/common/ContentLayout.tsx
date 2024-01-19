import { useRef, type ReactNode } from 'react';

import BottomNavigation from '../BottomNavigation';
import { cn } from '@/utils';

interface ContentLayoutProps {
  children: ReactNode;
  className?: string;
  isShowBottomNavigation?: boolean;
}

function ContentLayout({
  children,
  className,
  isShowBottomNavigation = true,
}: ContentLayoutProps) {
  const mainRef = useRef<HTMLElement | null>(null);

  return (
    <>
      <main
        className={cn(
          'px-[24px] mt-[44px] mb-[50px] overflow-y-scroll max-h-[calc(100vh-44px)] overscroll-contain',
          className,
        )}
        ref={mainRef}
      >
        {children}
      </main>
      {isShowBottomNavigation && (
        <footer className="w-[420px] h-[50px]">
          <BottomNavigation contentRef={mainRef} />
        </footer>
      )}
    </>
  );
}

export default ContentLayout;
