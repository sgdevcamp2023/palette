import { forwardRef, memo } from 'react';
import type { ForwardedRef } from 'react';
import { useNavigate } from '@tanstack/react-router';
import { useQueryClient, useSuspenseQuery } from '@tanstack/react-query';

import { apis } from '@/api';
import { Button } from './common';
import { usePaintAction, useProfileId } from '@/hooks';
import QuotePostBox from './QuotePostBox';
import { postDetailRoute } from '@/routes';
import type { TimelineItem } from '@/@types';
import Typography from './common/Typography';
import { cn, forCloudinaryImage } from '@/utils';
import TimelineItemMenu from './TimelineItemMenu';
import AccessibleIconButton from './AccessibleIconButton';
import {
  ReplyBottomSheet,
  ShareBottomSheet,
  ViewsBottomSheet,
} from './bottomSheet';

interface MainPostBoxProps {
  userId: string;
  className?: string;
}

const MainPostBox = forwardRef<HTMLDivElement, MainPostBoxProps>(
  ({ userId, className }, ref: ForwardedRef<HTMLDivElement>) => {
    const profileId = useProfileId();
    const isFollow = profileId === userId;
    const navigate = useNavigate();
    const params = postDetailRoute.useParams();
    const { data: post } = useSuspenseQuery({
      queryKey: ['paint', params.postId],
      queryFn: () => apis.paints.getPaintById(params.postId),
    });
    const hasMedia = post?.includes.medias.length > 0;

    const queryClient = useQueryClient();
    const paintAction = usePaintAction({
      userId,
      onLikeOrDislike: () => {
        queryClient.setQueryData<TimelineItem>(
          ['paint', params.postId],
          (prev) => {
            if (prev) {
              return { ...prev, like: !prev.like };
            }
            return post;
          },
        );
      },
    });

    return (
      <>
        <div id={post.id} className={cn('w-full', className)} ref={ref}>
          <div className="w-full flex gap-[6px]">
            <button
              type="button"
              className="flex"
              onClick={() =>
                navigate({
                  to: '/profile/$userId',
                  params: { userId: post.authorId },
                })
              }
            >
              <img
                src={forCloudinaryImage(post.authorImagePath, {
                  resize: true,
                  width: 100,
                  height: 100,
                })}
                alt={`${post.authorNickname}`}
                className="rounded-full w-[44px] h-[44px] min-w-[44px]"
              />
            </button>
            {/* 헤더 */}
            <div className="w-full flex justify-between relative">
              <div className="flex flex-col">
                <Typography size="headline-8" color="grey-600">
                  {post.authorNickname}
                </Typography>
                <Typography size="body-1" color="blueGrey-800">
                  {post.authorUsername}
                </Typography>
              </div>
              <div className="flex gap-[6px] items-center">
                {!isFollow && (
                  <Button
                    variant="filled"
                    className="w-[64px] h-[24px] py-[6px]"
                  >
                    <Typography size="headline-8" color="white">
                      팔로우
                    </Typography>
                  </Button>
                )}
                <AccessibleIconButton
                  iconType="threeDot"
                  label="더보기 버튼"
                  width={20}
                  height={20}
                  stroke="blueGrey-500"
                  className="relative transition-colors hover:bg-grey-200 rounded-full p-1"
                  onClick={() => paintAction.onClickMore(post.id)}
                />
              </div>
              {paintAction.isShowMoreMenu.id === post.id &&
                paintAction.isShowMoreMenu.show && (
                  <TimelineItemMenu
                    userId={post.authorId}
                    username={post.authorUsername}
                  />
                )}
            </div>
          </div>
          {post.text && (
            <Typography
              size="body-2"
              color="grey-600"
              className="whitespace-pre-line my-[24px]"
            >
              {post.text}
            </Typography>
          )}

          {hasMedia && (
            <img
              src={forCloudinaryImage(post.includes.medias[0].path, {
                resize: false,
              })}
              alt="user-upload-asset"
              className="w-full rounded-[12px] mb-[24px]"
            />
          )}

          {/* Quote */}
          {post.quotePaint && (
            <QuotePostBox post={post.quotePaint} className="my-[24px]" />
          )}

          <div className="flex flex-col gap-[16px] divide-y divide-y-blueGrey400">
            <Typography as="span" size="body-2" color="blueGrey-800">
              {new Date(post.createdAt).toDateString()} ·
              <Typography
                as="span"
                size="headline-8"
                color="grey-600"
                className="mx-0.5"
              >
                {post.views}
              </Typography>
              회
            </Typography>
            <div className="flex gap-[2px]">
              {post.repaintCount > 0 && (
                <Typography
                  as="span"
                  size="body-2"
                  color="blueGrey-800"
                  className="pt-[12px]"
                >
                  재게시
                  <Typography
                    as="span"
                    size="headline-8"
                    color="grey-600"
                    className="mx-0.5"
                  >
                    {post.repaintCount}
                  </Typography>
                  회
                </Typography>
              )}
              {post.repaintCount > 0 && (
                <Typography
                  as="span"
                  size="body-2"
                  color="blueGrey-800"
                  className={`pt-[12px] ${post.repaintCount > 0 ? 'pl-2' : ''}`}
                >
                  인용
                  <Typography
                    as="span"
                    size="headline-8"
                    color="grey-600"
                    className="mx-0.5"
                  >
                    {post.repaintCount}
                  </Typography>
                  회
                </Typography>
              )}
            </div>
            <div className="flex gap-[2px]">
              {post.likeCount > 0 && (
                <Typography
                  as="span"
                  size="body-2"
                  color="blueGrey-800"
                  className="pt-[12px]"
                >
                  마음에 들어요
                  <Typography
                    as="span"
                    size="headline-8"
                    color="grey-600"
                    className="mx-0.5"
                  >
                    {post.likeCount}
                  </Typography>
                  회
                </Typography>
              )}
              {post.likeCount > 0 && (
                <Typography
                  as="span"
                  size="body-2"
                  color="blueGrey-800"
                  className={`pt-[12px] ${post.likeCount > 0 ? 'pl-2' : ''}`}
                >
                  북마크
                  <Typography
                    as="span"
                    size="headline-8"
                    color="grey-600"
                    className="mx-0.5"
                  >
                    {post.likeCount}
                  </Typography>
                  회
                </Typography>
              )}
            </div>

            {/* 페인트에 대한 아이콘 영역(footer) */}
            <div className="w-full flex justify-between py-[12px] px-[26px]">
              <AccessibleIconButton
                width={16}
                height={16}
                iconType="comment"
                label="답글 달기"
                fill="blueGrey-800"
                className="transition-colors hover:bg-grey-200 rounded-full p-1"
                onClick={() => paintAction.onClickReply(post.id)}
              />
              <div className="flex gap-[4px] items-center">
                <AccessibleIconButton
                  width={16}
                  height={16}
                  iconType="retweet"
                  stroke={post.repainted ? 'green-200' : undefined}
                  fill={post.repainted ? 'green-200' : undefined}
                  label="인용 혹은 재게시 하기"
                  className="transition-colors hover:bg-grey-200 rounded-full p-1"
                  onClick={() => paintAction.onClickRetweet(post.id)}
                />
              </div>
              <div className="flex gap-[4px] items-center">
                <AccessibleIconButton
                  width={16}
                  height={16}
                  iconType={post.like ? 'solidHeart' : 'heart'}
                  label="마음에 들어요 누르기"
                  className="transition-colors hover:bg-grey-200 rounded-full p-1"
                  onClick={() => paintAction.onClickHeart(post.id, post.like)}
                />
              </div>
              <div className="flex gap-[4px] items-center">
                <AccessibleIconButton
                  width={16}
                  height={16}
                  iconType="barChart"
                  label="조회수 보기"
                  className="transition-colors hover:bg-grey-200 rounded-full p-1"
                  onClick={() => paintAction.onClickViews(post.id)}
                />
              </div>
              <AccessibleIconButton
                width={16}
                height={16}
                iconType="share"
                label="공유하기"
                className="transition-colors hover:bg-grey-200 rounded-full p-1"
                onClick={() => paintAction.onClickShare(post.id)}
              />
            </div>
          </div>
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
  },
);

const MemoizedMainPostBox = memo(MainPostBox);

export default MemoizedMainPostBox;
