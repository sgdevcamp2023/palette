import { forwardRef, memo, useEffect } from 'react';
import type { ForwardedRef, RefObject } from 'react';
import { useNavigate } from '@tanstack/react-router';
import { useSuspenseQuery } from '@tanstack/react-query';

import { cn } from '@/utils';
import { apis } from '@/api';
import type { PaintAction } from '@/hooks';
import { postDetailRoute } from '@/routes';
import TimelineItemBox from './TimelineItemBox';

interface BeforeTimelineListProps {
  className?: string;
  mainPostRef: RefObject<HTMLDivElement>;
  parentRef: RefObject<HTMLDivElement>;

  paintAction: PaintAction;
}

const BeforeTimelineList = forwardRef<HTMLDivElement, BeforeTimelineListProps>(
  (
    { className, paintAction, mainPostRef, parentRef },
    ref: ForwardedRef<HTMLDivElement>,
  ) => {
    const navigate = useNavigate();
    const params = postDetailRoute.useParams();
    const { data: posts, isSuccess } = useSuspenseQuery({
      queryKey: ['post', params.postId, 'before'],
      queryFn: () => apis.paints.getBeforePaintsById(params.postId),
    });

    if (Array.isArray(posts) && posts.length === 0) {
      return <div ref={ref} id="-1" />;
    }
    useEffect(() => {
      const $mainPost = mainPostRef.current;
      const $parent = parentRef.current;

      if ($mainPost && $parent) {
        const headerOffset = 68;
        $mainPost.scrollIntoView();
        $parent.scrollBy(0, -headerOffset);
      }
    }, [mainPostRef.current?.id, isSuccess]);

    return (
      <div
        ref={ref}
        className={cn('flex flex-col gap-[12px] w-full h-full', className)}
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
            isLazyImage={false}
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

const MemoizedBeforeTimelineList = memo(BeforeTimelineList);

export default MemoizedBeforeTimelineList;
