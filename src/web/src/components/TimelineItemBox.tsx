import { cn, getDiffDateText } from '@/utils';
import type { TimelineItem } from '@/@types';
import Typography from './common/Typography';
import TimelineItemMenu from './TimelineItemMenu';
import AccessibleIconButton from './AccessibleIconButton';

interface TimelineItemBoxProps {
  item: TimelineItem;
  isShowMenu: boolean;
  className?: string;
  onClickReply: VoidFunction;
  onClickRetweet: VoidFunction;
  onClickHeart: VoidFunction;
  onClickViews: VoidFunction;
  onClickShare: VoidFunction;
  onClickMore: VoidFunction;
}

function TimelineItemBox({
  item,
  isShowMenu,
  className,
  onClickReply,
  onClickRetweet,
  onClickHeart,
  onClickViews,
  onClickShare,
  onClickMore,
}: TimelineItemBoxProps) {
  const hasMedia = item.includes.medias.length > 0;

  return (
    <section className={cn('w-full flex gap-[8px]', className)}>
      <img
        src={item.authorImagePath}
        alt={`${item.authorNickname}`}
        className="rounded-full w-[44px] h-[44px] min-w-[44px]"
      />
      <div className="w-full">
        {/* 헤더 */}
        <div className="w-full flex justify-between relative">
          <div className="flex gap-[4px] items-center items-center">
            <Typography size="headline-8" color="grey-600">
              {item.authorNickname}
            </Typography>
            <Typography size="body-1" color="blueGrey-800">
              {item.authorUsername} ·{' '}
              {getDiffDateText(item.createdAt, new Date())}
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
              userId={item.authorId}
              username={item.authorUsername}
            />
          )}
        </div>

        {item.text && (
          <Typography
            size="body-2"
            color="grey-600"
            className="mt-[2px] mb-[12px] whitespace-pre-line"
          >
            {item.text}
          </Typography>
        )}

        {hasMedia && (
          <img
            src={item.includes.medias[0].path}
            alt="user-upload-asset"
            className="w-full max-h-[300px] rounded-[10px] min-h-[300px]"
          />
        )}

        {/* 페인트에 대한 아이콘 영역(footer) */}
        <div className="w-full flex justify-between mt-[8px]">
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
              stroke={item.repainted ? 'green-200' : undefined}
              fill={item.repainted ? 'green-200' : undefined}
              label="인용 혹은 재게시 하기"
              className="transition-colors hover:bg-grey-200 rounded-full p-1"
              onClick={onClickRetweet}
            />
            <Typography
              size="body-3"
              color={item.repainted ? 'green-200' : 'blueGrey-800'}
            >
              {item.repaintCount}
            </Typography>
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
            <Typography
              size="body-3"
              color={item.like ? 'red-100' : 'blueGrey-800'}
            >
              {item.likeCount}
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
              {item.views}
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
    </section>
  );
}

export default TimelineItemBox;
