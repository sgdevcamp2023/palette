import { memo } from 'react';
import type { MouseEvent } from 'react';
import { useNavigate } from '@tanstack/react-router';

import type { TimelineItem } from '@/@types';
import { cn, forCloudinaryImage, getDiffDateText } from '@/utils';
import { Typography } from './common';

interface QuotePostBoxProps {
  post: TimelineItem;
  direction?: 'vertical' | 'horizontal';
  className?: string;
}

function QuotePostBox({
  post,
  className,
  direction = 'vertical',
}: QuotePostBoxProps) {
  const navigate = useNavigate();
  const hasMedia = post.includes.medias.length > 0;

  const handleClickQuoteBox = (
    e: MouseEvent<HTMLButtonElement, globalThis.MouseEvent>,
  ) => {
    e.stopPropagation();
    navigate({ to: '/post/$postId', params: { postId: post.id } });
  };

  return (
    <div
      className={cn(
        'w-full rounded-[12px] border-[1px] border-blueGrey200',
        className,
      )}
    >
      <button type="button" className="text-left" onClick={handleClickQuoteBox}>
        <div className="flex gap-[4px] items-center px-[12px] pt-[12px]">
          <img
            src={forCloudinaryImage(post.authorImagePath)}
            alt={`${post.authorNickname}`}
            className="rounded-full w-[20px] h-[20px] min-w-[20px] max-w-[20px]"
          />
          <Typography size="headline-8" color="grey-600">
            {post.authorNickname}
          </Typography>
          <Typography size="body-1" color="blueGrey-800">
            {post.authorUsername} ·{' '}
            {getDiffDateText(post.createdAt, new Date())}
          </Typography>
        </div>

        <div
          className={cn(
            'flex gap-[4px]',
            direction === 'horizontal'
              ? 'flex-row-reverse px-[10px] pb-[10px]'
              : 'flex-col',
          )}
        >
          {post.text && (
            <Typography
              size="body-2"
              color="grey-600"
              className={cn(
                'whitespace-pre-line mt-[6px] px-[10px]',
                direction === 'horizontal' ? 'line-clamp-4' : 'line-clamp-5',
              )}
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
              className={cn(
                'mt-[8px]',
                direction === 'horizontal'
                  ? 'w-[80px] h-[80px] rounded-[12px]'
                  : 'w-full rounded-b-[10px]',
              )}
            />
          )}
        </div>
      </button>
    </div>
  );
}

const MemoizedQuotePostBox = memo(QuotePostBox);

export default MemoizedQuotePostBox;
