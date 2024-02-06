import { memo } from 'react';
import { useNavigate } from '@tanstack/react-router';
import { useSuspenseQuery } from '@tanstack/react-query';

import { apis } from '@/api';
import { usePaintAction } from '@/hooks';
import type { TimelineItem } from '@/@types';
import TimelineItemBox from './TimelineItemBox';
import { createDummyTimelineItem } from '@/utils';
import ReplyBottomSheet from './bottomSheet/ReplyBottomSheet';
import ShareBottomSheet from './bottomSheet/ShareBottomSheet';
import ViewsBottomSheet from './bottomSheet/ViewsBottomSheet';

interface TimelineItemListProps {
  type: 'follow' | 'recommend' | 'my-post' | 'my-reply' | 'media' | 'heart';
}

function delay(ms: number): Promise<TimelineItem[]> {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve(createDummyTimelineItem(10));
    }, ms);
  });
}

function getQueryFnByType(
  type: TimelineItemListProps['type'],
): Promise<TimelineItem[]> {
  switch (type) {
    case 'follow':
      return apis.auth.logout() as unknown as Promise<TimelineItem[]>;
    case 'recommend':
      return delay(1250);
    case 'my-post':
      return delay(1250);
    case 'my-reply':
      return delay(1250);
    case 'media':
      return delay(1250);
    case 'heart':
      return delay(1250);
    default:
      return delay(1250);
  }
}

function TimelineItemList({ type }: TimelineItemListProps) {
  const { data: paints } = useSuspenseQuery({
    queryKey: ['paint', type],
    queryFn: () => getQueryFnByType(type),
  });

  const navigate = useNavigate();
  const paintAction = usePaintAction();

  return (
    <>
      <div
        onScroll={paintAction.onScrollLayout}
        className="flex flex-col gap-[12px] w-full h-full divide-y divide-blueGrey-400"
      >
        {paints.map((paint) => (
          <TimelineItemBox
            key={paint.id}
            post={paint}
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
      </div>
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

const MemoizedTimelineItemList = memo(TimelineItemList);

export default MemoizedTimelineItemList;
