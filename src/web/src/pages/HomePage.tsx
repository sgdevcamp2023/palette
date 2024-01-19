import { useState } from 'react';

import type { TimelineItem } from '@/@types';
import { createDummyTimelineItem } from '@/utils';
import { Tabs, Header, ContentLayout, TimelineItemBox } from '@/components';
import { ReplyBottomSheet } from '@/components/bottomSheet';

interface BottomSheetState {
  reply: boolean;
  views: boolean;
  share: boolean;
}

const INITIAL_BOTTOM_SHEET_OPEN: BottomSheetState = {
  reply: false,
  views: false,
  share: false,
};

function HomePage() {
  const [isBottomSheetOpen, setIsBottomSheetOpen] = useState<BottomSheetState>(
    INITIAL_BOTTOM_SHEET_OPEN,
  );
  const [paints] = useState<TimelineItem[]>(() => createDummyTimelineItem(10));
  const [selectedPostId, setSelectedPostId] = useState<TimelineItem['id']>('');

  const handleClickTimelineActionIcon = (
    id: string,
    type: keyof BottomSheetState,
  ) => {
    setSelectedPostId(id);
    setIsBottomSheetOpen((prev) => ({ ...prev, [type]: !prev[type] }));
  };

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
                    onClickReply={() =>
                      handleClickTimelineActionIcon(paint.id, 'reply')
                    }
                    onClickRetweet={() =>
                      handleClickTimelineActionIcon(paint.id, 'reply')
                    }
                    onClickHeart={() =>
                      handleClickTimelineActionIcon(paint.id, 'reply')
                    }
                    onClickViews={() =>
                      handleClickTimelineActionIcon(paint.id, 'reply')
                    }
                    onClickShare={() =>
                      handleClickTimelineActionIcon(paint.id, 'reply')
                    }
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
                      onClickReply={() =>
                        handleClickTimelineActionIcon(paint.id, 'reply')
                      }
                      onClickRetweet={() =>
                        handleClickTimelineActionIcon(paint.id, 'reply')
                      }
                      onClickHeart={() =>
                        handleClickTimelineActionIcon(paint.id, 'reply')
                      }
                      onClickViews={() =>
                        handleClickTimelineActionIcon(paint.id, 'reply')
                      }
                      onClickShare={() =>
                        handleClickTimelineActionIcon(paint.id, 'reply')
                      }
                    />
                  ))}
              </ContentLayout>
            ),
          },
        ]}
        className="mt-[44px]"
      />
      <ReplyBottomSheet
        id={selectedPostId}
        isOpen={isBottomSheetOpen.reply}
        onClose={() =>
          setIsBottomSheetOpen((prev) => ({ ...prev, reply: false }))
        }
      />
    </>
  );
}

export default HomePage;
