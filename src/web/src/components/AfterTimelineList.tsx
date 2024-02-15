import { forwardRef, memo } from 'react';
import type { ForwardedRef } from 'react';
import { useNavigate } from '@tanstack/react-router';
import { useSuspenseQuery } from '@tanstack/react-query';

import { apis } from '@/api';
import { cn } from '@/utils';
import type { PaintAction } from '@/hooks';
import { postDetailRoute } from '@/routes';
import TimelineItemBox from './TimelineItemBox';

interface AfterTimelineListProps {
  className?: string;
  paintAction: PaintAction;
}

const AfterTimelineList = forwardRef<HTMLDivElement, AfterTimelineListProps>(
  ({ className, paintAction }, ref: ForwardedRef<HTMLDivElement>) => {
    const navigate = useNavigate();
    const params = postDetailRoute.useParams();
    const { data: posts } = useSuspenseQuery({
      queryKey: ['post', params.postId, 'before'],
      queryFn: () => apis.paints.getBeforePaintsById(params.postId),
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
                search: { postId: post.id },
              })
            }
            onClickRetweet={() => paintAction.onClickRetweet(post.id)}
            onClickHeart={() => paintAction.onClickHeart(post.id)}
            onClickViews={() => paintAction.onClickViews(post.id)}
            onClickShare={() => paintAction.onClickShare(post.id)}
            onClickMore={() => paintAction.onClickMore(post.id)}
          />
        ))}
      </div>
    );
  },
);

const MemoizedAfterTimelineList = memo(AfterTimelineList);

export default MemoizedAfterTimelineList;
