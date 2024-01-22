import { toast } from 'react-toastify';
import type { ComponentProps } from 'react';

import type { TimelineItem } from '@/@types';
import { BottomSheet, Icon, Typography } from '../common';

interface ShareBottomSheetProps {
  id: TimelineItem['id'];
  isOpen: ComponentProps<typeof BottomSheet>['isOpen'];
  onClose: ComponentProps<typeof BottomSheet>['onClose'];
}

function ShareBottomSheet({ id, isOpen, onClose }: ShareBottomSheetProps) {
  const copyToClipboard = () => {
    navigator.clipboard.writeText(`http://localhost:5173/post/${id}`);
    toast('클립보드에 복사되었습니다.');
    onClose();
  };

  return (
    <BottomSheet buttonText="취소" isOpen={isOpen} onClose={onClose}>
      <div className="flex flex-col gap-[32px]">
        <div className="flex gap-[18px] items-center">
          <Icon type="clip" width={24} height={24} />
          <Typography
            role="button"
            size="body-1"
            color="grey-600"
            onClick={copyToClipboard}
          >
            링크 복사
          </Typography>
        </div>
        <div className="flex gap-[18px] items-center">
          <Icon type="share" width={24} height={24} />
          <Typography
            role="button"
            size="body-1"
            color="grey-600"
            onClick={() => toast('아직 공유하기는 지원되지 않습니다.')}
          >
            공유 하기
          </Typography>
        </div>
      </div>
    </BottomSheet>
  );
}

export default ShareBottomSheet;
