import { useRef, type ReactNode } from 'react';

import BottomNavigation from './BottomNavigation';

interface ContentLayoutProps {
  children: ReactNode;
}

function ContentLayout({ children }: ContentLayoutProps) {
  const mainRef = useRef<HTMLElement | null>(null);

  return (
    <>
      <main
        className="px-[24px] mt-[44px] mb-[50px] overflow-y-scroll max-h-[calc(100vh-44px)]"
        ref={mainRef}
      >
        {children}
      </main>
      <footer className="w-[420px] h-[50px]">
        <BottomNavigation contentRef={mainRef} />
      </footer>
    </>
  );
}

export default ContentLayout;