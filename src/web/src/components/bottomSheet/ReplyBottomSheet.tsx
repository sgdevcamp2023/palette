import type { ComponentProps } from 'react';
import { Link } from '@tanstack/react-router';
import { useMutation, useQuery } from '@tanstack/react-query';

import { apis } from '@/api';
import type { TimelineItem, User } from '@/@types';
import { BottomSheet, Icon, Typography } from '../common';

interface ReplyBottomSheetProps {
  id: TimelineItem['id'];
  isOpen: ComponentProps<typeof BottomSheet>['isOpen'];
  onClose: ComponentProps<typeof BottomSheet>['onClose'];
}

function ReplyBottomSheet({ id, isOpen, onClose }: ReplyBottomSheetProps) {
  const { data: me } = useQuery({
    queryKey: ['user-profile', 'me'],
    queryFn: () => apis.users.getMyProfile(),
  });

  const repaintMutate = useMutation({
    mutationKey: ['re-paint', id],
    mutationFn: (userId: User['id']) =>
      apis.users.rePaint({ userId, paintId: id }),
  });

  const handleClickRepaint = () => {
    if (!me) {
      throw new Error('사용자가 없습니다.');
    }
    repaintMutate.mutate(me.id);
  };

  return (
    <BottomSheet buttonText="취소" isOpen={isOpen} onClose={onClose}>
      <div className="flex flex-col gap-[32px]">
        <div className="flex gap-[18px] items-center">
          <Icon type="retweet" width={24} height={24} />
          <div tabIndex={0} role="button" onClick={handleClickRepaint}>
            <Typography role="button" size="body-1" color="grey-600">
              재게시
            </Typography>
          </div>
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
