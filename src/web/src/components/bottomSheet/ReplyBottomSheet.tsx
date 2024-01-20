import type { ComponentProps } from 'react';
import { Link } from '@tanstack/react-router';

import type { TimelineItem } from '@/@types';
import { BottomSheet, Icon, Typography } from '../common';

interface ReplyBottomSheetProps {
  id: TimelineItem['id'];
  isOpen: ComponentProps<typeof BottomSheet>['isOpen'];
  onClose: ComponentProps<typeof BottomSheet>['onClose'];
}

function ReplyBottomSheet({ id, isOpen, onClose }: ReplyBottomSheetProps) {
  return (
    <BottomSheet buttonText="취소" isOpen={isOpen} onClose={onClose}>
      <div className="flex flex-col gap-[32px]">
        <div className="flex gap-[18px] items-center">
          <Icon type="retweet" width={24} height={24} />
          <Link to="/post/edit" search={{ postId: id }}>
            <Typography role="button" size="body-1" color="grey-600">
              재게시
            </Typography>
          </Link>
        </div>
        <div className="flex gap-[18px] items-center">
          <Icon type="pen" width={24} height={24} />
          <Link to="/post/edit" search={{ postId: id }}>
            <Typography role="button" size="body-1" color="grey-600">
              인용
            </Typography>
          </Link>
        </div>
      </div>
    </BottomSheet>
  );
}

export default ReplyBottomSheet;
