import { useState } from 'react';

import type { TimelineItem } from '@/@types';
import { createDummyTimelineItem } from '@/utils';
import {
  Icon,
  Tabs,
  Header,
  Typography,
  BottomSheet,
  ContentLayout,
  TimelineItemBox,
} from '@/components';

function HomePage() {
  const [paints] = useState<TimelineItem[]>(() => createDummyTimelineItem(10));
  const [isBottomSheetOpen, setIsBottomSheetOpen] = useState<boolean>(false);

  return (
    <>
      <Header
        left={{
          type: 'circlePerson',
          label: '취소',
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
              <ContentLayout className="flex flex-col gap-[12px] pl-[12px] pr-[4px] mt-0 pb-[50px] max-h-[calc(100%-94px)] divide-y divide-blueGrey-400">
                {paints.map((paint) => (
                  <TimelineItemBox
                    key={paint.id}
                    item={paint}
                    className="pt-[10px]"
                    onClickReply={() => setIsBottomSheetOpen((prev) => !prev)}
                    onClickRetweet={() => setIsBottomSheetOpen((prev) => !prev)}
                    onClickHeart={() => setIsBottomSheetOpen((prev) => !prev)}
                    onClickViews={() => setIsBottomSheetOpen((prev) => !prev)}
                    onClickShare={() => setIsBottomSheetOpen((prev) => !prev)}
                  />
                ))}
              </ContentLayout>
            ),
          },
          {
            label: '팔로우 중',
            content: (
              <ContentLayout className="flex flex-col gap-[12px] pl-[12px] pr-[4px] mt-0 pb-[50px] max-h-[calc(100%-94px)] divide-y divide-blueGrey-400">
                {[...paints]
                  .sort((a, b) => Number(b.id) - Number(a.id))
                  .map((paint) => (
                    <TimelineItemBox
                      key={paint.id}
                      item={paint}
                      className="pt-[10px]"
                      onClickReply={() => setIsBottomSheetOpen((prev) => !prev)}
                      onClickRetweet={() =>
                        setIsBottomSheetOpen((prev) => !prev)
                      }
                      onClickHeart={() => setIsBottomSheetOpen((prev) => !prev)}
                      onClickViews={() => setIsBottomSheetOpen((prev) => !prev)}
                      onClickShare={() => setIsBottomSheetOpen((prev) => !prev)}
                    />
                  ))}
              </ContentLayout>
            ),
          },
        ]}
        className="mt-[44px]"
      />
      <BottomSheet
        buttonText="취소"
        isOpen={isBottomSheetOpen}
        onClose={() => setIsBottomSheetOpen(false)}
      >
        <div className="flex flex-col gap-[32px]">
          <div className="flex gap-[18px] items-center">
            <Icon type="retweet" width={24} height={24} />
            <Typography size="body-1" color="grey-600">
              재게시
            </Typography>
          </div>
          <div className="flex gap-[18px] items-center">
            <Icon type="pen" width={24} height={24} />
            <Typography size="body-1" color="grey-600">
              인용
            </Typography>
          </div>
        </div>
      </BottomSheet>
    </>
  );
}

export default HomePage;
