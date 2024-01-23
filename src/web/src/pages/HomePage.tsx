import { useState } from 'react';
import { useNavigate } from '@tanstack/react-router';

import { Tabs, Header, ContentLayout, TimelineItemBox } from '@/components';
import {
  ReplyBottomSheet,
  ShareBottomSheet,
  ViewsBottomSheet,
} from '@/components/bottomSheet';
import { usePaintAction } from '@/hooks';
import type { TimelineItem } from '@/@types';
import { createDummyTimelineItem } from '@/utils';

function HomePage() {
  const navigate = useNavigate();
  const paintAction = usePaintAction();
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
              <ContentLayout
                className="flex flex-col gap-[12px] pl-[12px] pr-[4px] mt-0 pb-[50px] max-h-[calc(100%-94px)] divide-y divide-blueGrey-400"
                onScroll={paintAction.onScrollLayout}
              >
                {paints.map((paint) => (
                  <TimelineItemBox
                    key={paint.id}
                    item={paint}
                    className="pt-[10px]"
                    isShowMenu={
                      paintAction.isShowMoreMenu.id === paint.id &&
                      paintAction.isShowMoreMenu.show
                    }
                    onClickReply={() =>
                      navigate({
                        to: '/post/edit',
                        search: { postId: paint.id },
                      })
                    }
                    onClickRetweet={() => paintAction.onClickRetweet(paint.id)}
                    onClickHeart={() => paintAction.onClickHeart(paint.id)}
                    onClickViews={() => paintAction.onClickViews(paint.id)}
                    onClickShare={() => paintAction.onClickShare(paint.id)}
                    onClickMore={() => paintAction.onClickMore(paint.id)}
                  />
                ))}
              </ContentLayout>
            ),
          },
          {
            label: '팔로우 중',
            content: (
              <ContentLayout className="flex flex-col gap-[12px] pl-[12px] pr-[4px] mt-0 pb-[50px] max-h-[calc(100%-94px)] divide-y divide-blueGrey-400">
                {[...paints].reverse().map((paint) => (
                  <TimelineItemBox
                    key={paint.id}
                    item={paint}
                    className="pt-[10px]"
                    isShowMenu={
                      paintAction.isShowMoreMenu.id === paint.id &&
                      paintAction.isShowMoreMenu.show
                    }
                    onClickReply={() =>
                      navigate({
                        to: '/post/edit',
                        search: { postId: paint.id },
                      })
                    }
                    onClickRetweet={() => paintAction.onClickRetweet(paint.id)}
                    onClickHeart={() => paintAction.onClickHeart(paint.id)}
                    onClickViews={() => paintAction.onClickViews(paint.id)}
                    onClickShare={() => paintAction.onClickShare(paint.id)}
                    onClickMore={() => paintAction.onClickMore(paint.id)}
                  />
                ))}
              </ContentLayout>
            ),
          },
        ]}
        className="mt-[44px]"
      />
      <ReplyBottomSheet
        id={paintAction.selectedPostId}
        isOpen={paintAction.isBottomSheetOpen.reply}
        onClose={() => paintAction.onCloseBottomSheet('reply')}
      />
      <ViewsBottomSheet
        isOpen={paintAction.isBottomSheetOpen.views}
        onClose={() => paintAction.onCloseBottomSheet('views')}
      />
      <ShareBottomSheet
        id={paintAction.selectedPostId}
        isOpen={paintAction.isBottomSheetOpen.share}
        onClose={() => paintAction.onCloseBottomSheet('share')}
      />
    </>
  );
}

export default HomePage;
