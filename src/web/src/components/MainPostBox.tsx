import { forwardRef } from 'react';
import type { ForwardedRef } from 'react';

import { cn } from '@/utils';
import { Button } from './common';
import type { TimelineItem } from '@/@types';
import Typography from './common/Typography';
import TimelineItemMenu from './TimelineItemMenu';
import AccessibleIconButton from './AccessibleIconButton';

interface MainPostBoxProps {
  item: TimelineItem;
  isFollow: boolean;
  isShowMenu: boolean;
  className?: string;
  onClickReply: VoidFunction;
  onClickRetweet: VoidFunction;
  onClickHeart: VoidFunction;
  onClickViews: VoidFunction;
  onClickShare: VoidFunction;
  onClickMore: VoidFunction;
}

const MainPostBox = forwardRef<HTMLDivElement, MainPostBoxProps>(
  (
    {
      item,
      isFollow,
      isShowMenu,
      className,
      onClickReply,
      onClickRetweet,
      onClickHeart,
      onClickViews,
      onClickShare,
      onClickMore,
    },
    ref: ForwardedRef<HTMLDivElement>,
  ) => {
    const hasMedia = item.includes.medias.length > 0;

    return (
      <div className={cn('w-full', className)} ref={ref}>
        <div className="w-full flex gap-[6px]">
          <img
            src={item.authorImagePath}
            alt={`${item.authorNickname}`}
            className="rounded-full w-[44px] h-[44px] min-w-[44px]"
          />
          {/* 헤더 */}
          <div className="w-full flex justify-between relative">
            <div className="flex flex-col">
              <Typography size="headline-8" color="grey-600">
                {item.authorNickname}
              </Typography>
              <Typography size="body-1" color="blueGrey-800">
                {item.authorUsername}
              </Typography>
            </div>
            <div className="flex gap-[6px]">
              {!isFollow && (
                <Button variant="filled" className="w-[56px] h-[24px]">
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
                onClick={onClickMore}
              />
            </div>
            {isShowMenu && (
              <TimelineItemMenu
                userId={item.authorId}
                username={item.authorUsername}
              />
            )}
          </div>
        </div>
        {item.text && (
          <Typography
            size="body-2"
            color="grey-600"
            className="whitespace-pre-line my-[24px]"
          >
            {item.text}
          </Typography>
        )}

        {hasMedia && (
          <img
            src={item.includes.medias[0].path}
            alt="user-upload-asset"
            className="w-full max-h-[300px] rounded-[12px] mb-[24px]"
          />
        )}

        <div className="flex flex-col gap-[16px] divide-y divide-y-blueGrey400">
          <Typography as="span" size="body-2" color="blueGrey-800">
            {item.createdAt.toDateString()} ·
            <Typography
              as="span"
              size="headline-8"
              color="grey-600"
              className="mx-0.5"
            >
              {item.views}
            </Typography>
            회
          </Typography>
          <div className="flex gap-[2px]">
            {item.repaintCount > 0 && (
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
                  {item.repaintCount}
                </Typography>
                회
              </Typography>
            )}
            {item.repaintCount > 0 && (
              <Typography
                as="span"
                size="body-2"
                color="blueGrey-800"
                className={`pt-[12px] ${item.repaintCount > 0 ? 'pl-2' : ''}`}
              >
                인용
                <Typography
                  as="span"
                  size="headline-8"
                  color="grey-600"
                  className="mx-0.5"
                >
                  {item.repaintCount}
                </Typography>
                회
              </Typography>
            )}
          </div>
          <div className="flex gap-[2px]">
            {item.likeCount > 0 && (
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
                  {item.likeCount}
                </Typography>
                회
              </Typography>
            )}
            {item.likeCount > 0 && (
              <Typography
                as="span"
                size="body-2"
                color="blueGrey-800"
                className={`pt-[12px] ${item.likeCount > 0 ? 'pl-2' : ''}`}
              >
                북마크
                <Typography
                  as="span"
                  size="headline-8"
                  color="grey-600"
                  className="mx-0.5"
                >
                  {item.likeCount}
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
              onClick={onClickReply}
            />
            <div className="flex gap-[4px] items-center">
              <AccessibleIconButton
                width={16}
                height={16}
                iconType="retweet"
                stroke={item.repainted ? 'green-200' : undefined}
                fill={item.repainted ? 'green-200' : undefined}
                label="인용 혹은 재게시 하기"
                className="transition-colors hover:bg-grey-200 rounded-full p-1"
                onClick={onClickRetweet}
              />
            </div>
            <div className="flex gap-[4px] items-center">
              <AccessibleIconButton
                width={16}
                height={16}
                iconType={item.like ? 'solidHeart' : 'heart'}
                label="마음에 들어요 누르기"
                className="transition-colors hover:bg-grey-200 rounded-full p-1"
                onClick={onClickHeart}
              />
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
  },
);

export default MainPostBox;
