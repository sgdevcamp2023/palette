import type { ReactNode } from 'react';
import { useEffect, useRef, useState } from 'react';

import { ContentLayout, Typography } from '@/components';
import { cn } from '@/utils';

interface TabProps {
  tabs: {
    label: string;
    content: ReactNode;
  }[];
  className?: string;
}

function Tabs({ tabs, className }: TabProps) {
  const [activeTabIndex, setActiveTabIndex] = useState<number>(0);
  const [tabUnderlineLeft, setTabUnderlineLeft] = useState<number>(0);
  const [tabUnderlineWidth, setTabUnderlineWidth] = useState<number>(0);

  const tabsRef = useRef<(HTMLButtonElement | null)[]>([]);

  useEffect(() => {
    function setTabPosition() {
      const MARGIN = 20;
      const currentTab = tabsRef.current[activeTabIndex];
      if (currentTab) {
        setTabUnderlineLeft(currentTab.offsetLeft - MARGIN / 2);
        setTabUnderlineWidth(currentTab.clientWidth + MARGIN);
      }
    }
    setTabPosition();

    window.addEventListener('resize', setTabPosition);

    return () => window.removeEventListener('resize', setTabPosition);
  }, [activeTabIndex]);

  return (
    <>
      <div
        className={cn(
          'relative w-full border-b-[1px] border-blueGrey-500',
          className,
        )}
      >
        <div className="w-full flex justify-around pb-[10px]">
          {tabs.map((tab, index) => (
            <button
              type="button"
              key={tab.label}
              ref={(el) => {
                tabsRef.current[index] = el;
              }}
              onClick={() => setActiveTabIndex(index)}
            >
              <Typography
                as="span"
                size="headline-8"
                color={activeTabIndex === index ? 'grey-600' : 'blueGrey-800'}
                className="transition-colors hover:grey-400"
              >
                {tab.label}
              </Typography>
            </button>
          ))}
        </div>
        <span
          className="absolute bottom-0 block h-[3px] rounded-[4px] bg-blue-500 transition-all duration-300"
          style={{ left: tabUnderlineLeft, width: tabUnderlineWidth }}
        />
      </div>
      <ContentLayout className="mt-0">
        {tabs[activeTabIndex].content}
      </ContentLayout>
    </>
  );
}

export default Tabs;
