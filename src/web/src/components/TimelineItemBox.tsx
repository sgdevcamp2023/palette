import { memo } from 'react';
import { useNavigate } from '@tanstack/react-router';

import { LazyImage } from './common';
import QuotePostBox from './QuotePostBox';
import type { TimelineItem } from '@/@types';
import Typography from './common/Typography';
import TimelineItemMenu from './TimelineItemMenu';
import AccessibleIconButton from './AccessibleIconButton';
import { cn, forCloudinaryImage, getDiffDateText } from '@/utils';

interface TimelineItemBoxProps {
  post: TimelineItem;
  isShowMenu: boolean;
  isLazyImage?: boolean;
  className?: string;
  onClickReply: VoidFunction;
  onClickRetweet: VoidFunction;
  onClickHeart: VoidFunction;
  onClickViews: VoidFunction;
  onClickShare: VoidFunction;
  onClickMore: VoidFunction;
}

function TimelineItemBox({
  post,
  isShowMenu,
  isLazyImage = true,
  className,
  onClickReply,
  onClickRetweet,
  onClickHeart,
  onClickViews,
  onClickShare,
  onClickMore,
}: TimelineItemBoxProps) {
  const navigate = useNavigate();
  const hasMedia = post.includes.medias.length > 0;

  return (
    <div className={cn('w-full flex gap-[8px]', className)}>
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
        <LazyImage
          src={forCloudinaryImage(post.authorImagePath, {
            resize: true,
            width: 100,
            height: 100,
          })}
          alt={`${post.authorNickname}`}
          className="rounded-full w-[44px] h-[44px] min-w-[44px]"
        />
      </button>
      <div
        role="button"
        tabIndex={0}
        className="w-full text-left"
        onClick={() => {
          navigate({
            to: '/post/$postId',
            params: { postId: post.id },
          });
        }}
      >
        {/* 헤더 */}
        <div className="w-full flex justify-between relative">
          <div className="flex gap-[4px] items-center">
            <Typography size="headline-8" color="grey-600">
              {post.authorNickname}
            </Typography>
            <Typography size="body-1" color="blueGrey-800">
              {post.authorUsername} ·{' '}
              {getDiffDateText(post.createdAt, new Date())}
            </Typography>
          </div>
          <AccessibleIconButton
            iconType="threeDot"
            label="더보기 버튼"
            width={20}
            height={20}
            stroke="blueGrey-500"
            className="relative transition-colors hover:bg-grey-200 rounded-full p-1"
            onClick={onClickMore}
          />
          {isShowMenu && (
            <TimelineItemMenu
              userId={post.authorId}
              username={post.authorUsername}
            />
          )}
        </div>

        {post.text && (
          <Typography
            size="body-2"
            color="grey-600"
            className="whitespace-pre-line"
          >
            {post.text}
          </Typography>
        )}

        {hasMedia &&
          (isLazyImage ? (
            <LazyImage
              src={forCloudinaryImage(post.includes.medias[0].path, {
                resize: false,
              })}
              alt="user-upload-asset"
              className="w-full rounded-[10px] mt-[8px] mb-[12px]"
            />
          ) : (
            <img
              src={forCloudinaryImage(post.includes.medias[0].path, {
                resize: false,
              })}
              alt="user-upload-asset"
              className="w-full rounded-[10px] mt-[8px] mb-[12px]"
            />
          ))}

        {/* Quote */}
        {post.includes.paint && (
          <QuotePostBox
            post={post.includes.paint}
            className="my-[8px]"
            direction={
              hasMedia && post.includes.paint.includes.medias.length > 0
                ? 'horizontal'
                : 'vertical'
            }
          />
        )}

        {/* 페인트에 대한 아이콘 영역(footer) */}
        <div className="w-full flex justify-between">
          <AccessibleIconButton
            width={16}
            height={16}
            iconType="comment"
            label="답글 달기"
            className="transition-colors hover:bg-grey-200 rounded-full p-1"
            onClick={onClickReply}
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
              onClick={onClickRetweet}
            />
            <Typography
              size="body-3"
              color={post.repainted ? 'green-200' : 'blueGrey-800'}
            >
              {post.repaintCount}
            </Typography>
          </div>
          <div className="flex gap-[4px] items-center">
            <AccessibleIconButton
              width={16}
              height={16}
              iconType={post.like ? 'solidHeart' : 'heart'}
              label="마음에 들어요 누르기"
              className="transition-colors hover:bg-grey-200 rounded-full p-1"
              onClick={onClickHeart}
            />
            <Typography
              size="body-3"
              color={post.like ? 'pink-100' : 'blueGrey-800'}
            >
              {post.likeCount}
            </Typography>
          </div>
          <div className="flex gap-[4px] items-center">
            <AccessibleIconButton
              width={16}
              height={16}
              iconType="barChart"
              label="조회수 보기"
              className="transition-colors hover:bg-grey-200 rounded-full p-1"
              onClick={onClickViews}
            />
            <Typography size="body-3" color="blueGrey-800">
              {post.views}
            </Typography>
          </div>
          <AccessibleIconButton
            width={16}
            height={16}
            iconType="share"
            label="공유하기"
            className="transition-colors hover:bg-grey-200 rounded-full p-1"
            onClick={onClickShare}
          />
        </div>
      </div>
    </div>
  );
}

const MemoizedTimelineItemBox = memo(TimelineItemBox);

export default MemoizedTimelineItemBox;
