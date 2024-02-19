import { memo } from 'react';
import { useNavigate } from '@tanstack/react-router';
import { useSuspenseQuery } from '@tanstack/react-query';

import { apis } from '@/api';
import { usePaintAction } from '@/hooks';
import type { TimelineItem, User } from '@/@types';
import TimelineItemBox from './TimelineItemBox';
import { cn, createDummyTimelineItem } from '@/utils';
import ReplyBottomSheet from './bottomSheet/ReplyBottomSheet';
import ShareBottomSheet from './bottomSheet/ShareBottomSheet';
import ViewsBottomSheet from './bottomSheet/ViewsBottomSheet';

interface TimelineItemListProps {
  type:
    | 'follow'
    | 'recommend'
    | 'post'
    | 'reply'
    | 'media'
    | 'heart'
    | 'search-recommend'
    | 'search-recent'
    | 'search-user'
    | 'search-media';
  className?: string;
  userId?: User['id'];
}

function delay(ms: number): Promise<TimelineItem[]> {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve(createDummyTimelineItem(10));
    }, ms);
  });
}

class UserNotFoundError extends Error {
  constructor() {
    super('유효하지 않는 사용자입니다.');
  }
}

function getQueryFnByType(
  type: TimelineItemListProps['type'],
  userId?: User['id'],
): Promise<TimelineItem[]> {
  switch (type) {
    case 'follow':
      return apis.auth.logout() as unknown as Promise<TimelineItem[]>;
    case 'recommend':
      return delay(1250);
    case 'post':
      if (!userId) throw new UserNotFoundError();
      return apis.users.getUserPaints(userId);
    case 'reply':
      if (!userId) throw new UserNotFoundError();
      return apis.users.getUserReplyPaints(userId);
    case 'media':
      if (!userId) throw new UserNotFoundError();
      return apis.users.getUserMediaPaints(userId);
    case 'heart':
      if (!userId) throw new UserNotFoundError();
      return apis.users.getUserLikePaints(userId);
    case 'search-recommend':
      return delay(1250);
    case 'search-recent':
      return delay(1250);
    case 'search-media':
      return delay(1250);
    case 'search-user':
      return delay(1250);
    default:
      return delay(1250);
  }
}

function TimelineItemList({ type, className, userId }: TimelineItemListProps) {
  const { data: paints } = useSuspenseQuery({
    queryKey: ['paint', type, userId],
    queryFn: () => getQueryFnByType(type, userId),
  });

  const navigate = useNavigate();
  const paintAction = usePaintAction();

  return (
    <>
      <div
        onScroll={paintAction.onScrollLayout}
        className={cn(
          'flex flex-col gap-[12px] w-full h-full divide-y divide-blueGrey-400',
          className,
        )}
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
