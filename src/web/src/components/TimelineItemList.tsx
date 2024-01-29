import { useNavigate } from '@tanstack/react-router';

import { usePaintAction } from '@/hooks';
import AsyncBoundary from './AsyncBoundary';
import type { TimelineItem } from '@/@types';
import TimelineItemBox from './TimelineItemBox';
import { TimelineItemListSkeleton } from './skeleton';
import ReplyBottomSheet from './bottomSheet/ReplyBottomSheet';
import ShareBottomSheet from './bottomSheet/ShareBottomSheet';
import ViewsBottomSheet from './bottomSheet/ViewsBottomSheet';

interface TimelineItemListProps {
  list: TimelineItem[];
}

function TimelineItemList({ list }: TimelineItemListProps) {
  const navigate = useNavigate();
  const paintAction = usePaintAction();

  return (
    <>
      <AsyncBoundary pendingFallback={<TimelineItemListSkeleton />}>
        <div
          onScroll={paintAction.onScrollLayout}
          className="flex flex-col gap-[12px] w-full h-full divide-y divide-blueGrey-400"
        >
          {list.map((paint) => (
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
        </div>
      </AsyncBoundary>
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

export default TimelineItemList;
