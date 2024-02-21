import { memo } from 'react';
import { useNavigate } from '@tanstack/react-router';
import { useSuspenseQuery } from '@tanstack/react-query';

import { apis } from '@/api';
import { usePaintAction, useProfileId } from '@/hooks';
import type { TimelineItem, User } from '@/@types';
import TimelineItemBox from './TimelineItemBox';
import { cn, createDummyTimelineItem } from '@/utils';
import ReplyBottomSheet from './bottomSheet/ReplyBottomSheet';
import ShareBottomSheet from './bottomSheet/ShareBottomSheet';
import ViewsBottomSheet from './bottomSheet/ViewsBottomSheet';
import { Typography } from './common';

interface TimelineItemListProps {
  type:
    | 'follow'
    | 'recommend'
    | 'post'
    | 'reply'
    | 'media'
    | 'heart'
    | 'bookmark'
    | 'search-recommend'
    | 'search-recent'
    | 'search-user'
    | 'search-media';
  className?: string;
}

function delay(ms: number): Promise<TimelineItem[]> {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve(createDummyTimelineItem(10));
    }, ms);
  });
}

function EmptyBox() {
  return (
    <div className="py-10 pb-32">
      <div className="flex flex-col gap-[40px] h-full justify-center items-center px-4">
        <div className="flex items-center justify-center w-[200px] h-[200px] rounded-full bg-yellow-100">
          <Typography size="headline-1" className="text-[100px]">
            🔥
          </Typography>
        </div>
        <div className="w-full flex flex-col justify-center items-center gap-3">
          <Typography size="headline-7" color="grey-600">
            게시글이 아직 없습니다.
          </Typography>
          <Typography size="body-1" color="grey-600">
            새로운 게시글을 작성해 보세요.
          </Typography>
        </div>
      </div>
    </div>
  );
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
    case 'recommend':
      return apis.timelines.getRecommendTimelineList();
    case 'follow':
      return apis.timelines.getFollowingTimelineList();
    case 'bookmark':
      if (!userId) throw new UserNotFoundError();
      return apis.users.getUserMarkPaints(userId);
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

function TimelineItemList({ type, className }: TimelineItemListProps) {
  const userId = useProfileId();
  const { data: paints } = useSuspenseQuery({
    queryKey: ['paint', type, userId],
    queryFn: () => getQueryFnByType(type, userId),
  });

  const navigate = useNavigate();
  const paintAction = usePaintAction({ userId });

  return (
    <>
      <div
        onScroll={paintAction.onScrollLayout}
        className={cn(
          'flex flex-col gap-[12px] w-full h-full divide-y divide-blueGrey-400',
          className,
        )}
      >
        {paints.length === 0 && <EmptyBox />}
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
            onClickHeart={() => paintAction.onClickHeart(paint.id, paint.like)}
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
