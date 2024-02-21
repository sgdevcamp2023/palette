import { forwardRef, memo } from 'react';
import type { ForwardedRef } from 'react';
import { useNavigate } from '@tanstack/react-router';
import { useQueryClient, useSuspenseQuery } from '@tanstack/react-query';

import { apis } from '@/api';
import { cn } from '@/utils';
import { usePaintAction } from '@/hooks';
import { postDetailRoute } from '@/routes';
import type { TimelineItem } from '@/@types';
import TimelineItemBox from './TimelineItemBox';

interface AfterTimelineListProps {
  userId: string;
  className?: string;
}

const AfterTimelineList = forwardRef<HTMLDivElement, AfterTimelineListProps>(
  ({ userId, className }, ref: ForwardedRef<HTMLDivElement>) => {
    const navigate = useNavigate();
    const params = postDetailRoute.useParams();
    const { data: posts } = useSuspenseQuery({
      queryKey: ['paint', params.postId, 'after'],
      queryFn: () => apis.paints.getAfterPaintsById(params.postId),
    });

    const queryClient = useQueryClient();
    const paintAction = usePaintAction({
      userId,
      onLikeOrDislike: (paintId) => {
        queryClient.setQueryData<TimelineItem[]>(
          ['paint', params.postId, 'after'],
          (prev) => {
            if (prev) {
              const nextPaints = [...prev];
              const willUpdateIndex = nextPaints.findIndex(
                (paint) => paint.id === paintId,
              );
              nextPaints[willUpdateIndex].like =
                !nextPaints[willUpdateIndex].like;
              nextPaints[willUpdateIndex].likeCount += nextPaints[
                willUpdateIndex
              ].like
                ? 1
                : -1;
              return nextPaints;
            }
            return [];
          },
        );
      },
    });

    if (Array.isArray(posts) && posts.length === 0) {
      return null;
    }

    return (
      <div
        ref={ref}
        className={cn(
          'flex flex-col gap-[12px] w-full h-full mb-[24px]',
          className,
        )}
      >
        {posts.map((post) => (
          <TimelineItemBox
            key={post.id}
            post={post}
            className="pt-[12px]"
            isShowMenu={
              paintAction.isShowMoreMenu.id === post.id &&
              paintAction.isShowMoreMenu.show
            }
            onClickReply={() =>
              navigate({
                to: '/post/edit',
                search: { inReplyToPaintId: post.id },
              })
            }
            onClickRetweet={() => paintAction.onClickRetweet(post.id)}
            onClickHeart={() => paintAction.onClickHeart(post.id, post.like)}
            onClickViews={() => paintAction.onClickViews(post.id)}
            onClickShare={() => paintAction.onClickShare(post.id)}
            onClickMore={() => paintAction.onClickMore(post.id)}
            onCloseMenu={paintAction.onCloseMenu}
          />
        ))}
      </div>
    );
  },
);

const MemoizedAfterTimelineList = memo(AfterTimelineList);

export default MemoizedAfterTimelineList;
