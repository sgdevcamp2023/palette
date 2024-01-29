import { useState } from 'react';

import type { TimelineItem } from '@/@types';
import { createDummyTimelineItem } from '@/utils';
import { Tabs, Header, ContentLayout, TimelineItemList } from '@/components';

function HomePage() {
  const [paints] = useState<TimelineItem[]>(() => createDummyTimelineItem(10));

  return (
    <>
      <Header
        left={{
          type: 'circlePerson',
          label: '메뉴 열기',
        }}
        center={{
          type: 'palette',
          label: '로고',
          width: 26,
        }}
        right={{
          type: 'setting',
          label: '로고',
        }}
      />
      <Tabs
        tabs={[
          {
            label: '추천',
            content: (
              <ContentLayout className="mt-0 pl-[12px] pr-[4px] pb-[50px] max-h-[calc(100%-94px)]">
                <TimelineItemList list={paints} />
              </ContentLayout>
            ),
          },
          {
            label: '팔로우 중',
            content: (
              <ContentLayout className="mt-0 pl-[12px] pr-[4px] pb-[50px] max-h-[calc(100%-94px)]">
                <TimelineItemList list={[...paints].reverse()} />
              </ContentLayout>
            ),
          },
        ]}
        className="mt-[44px]"
      />
    </>
  );
}

export default HomePage;
