import { memo } from 'react';
import type { MouseEvent } from 'react';
import { useNavigate } from '@tanstack/react-router';

import type { TimelineItem } from '@/@types';
import { cn, forCloudinaryImage, getDiffDateText } from '@/utils';
import { LazyImage, Typography } from './common';

interface QuotePostBoxProps {
  post: TimelineItem;
  direction?: 'vertical' | 'horizontal';
  className?: string;
}

const MAX_NAME_LENGTH = 8;

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
          <LazyImage
            src={forCloudinaryImage(post.authorImagePath, {
              resize: true,
              width: 100,
              height: 100,
              ratio: false,
              quality: 'auto:low',
            })}
            alt={`${post.authorNickname}`}
            className="rounded-full w-[20px] h-[20px] min-w-[20px] max-w-[20px] aspect-video"
          />
          <Typography size="headline-8" color="grey-600">
            {post.authorNickname.length > MAX_NAME_LENGTH
              ? `${post.authorNickname.slice(0, MAX_NAME_LENGTH)}...`
              : post.authorNickname}
          </Typography>
          <Typography size="body-1" color="blueGrey-800">
            {post.authorUsername.length > MAX_NAME_LENGTH
              ? `${post.authorUsername.slice(0, MAX_NAME_LENGTH)}...`
              : post.authorUsername}{' '}
            · {getDiffDateText(new Date(post.createdAt), new Date())}
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
            <LazyImage
              src={forCloudinaryImage(post.includes.medias[0].path, {
                resize: true,
                width: direction === 'vertical' ? 420 : 100,
                height: direction === 'vertical' ? 420 : 100,
                ratio: direction === 'vertical' ? '16:9' : '1:1',
              })}
              alt="user-upload-asset"
              className={cn(
                'mt-[8px] aspect-video',
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
