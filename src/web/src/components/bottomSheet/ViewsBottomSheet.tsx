import type { ComponentProps } from 'react';

import NotSupportText from '../NotSupportText';
import { BottomSheet, Typography } from '../common';

interface ViewsBottomSheetProps {
  isOpen: ComponentProps<typeof BottomSheet>['isOpen'];
  onClose: ComponentProps<typeof BottomSheet>['onClose'];
}

function ViewsBottomSheet({ isOpen, onClose }: ViewsBottomSheetProps) {
  return (
    <BottomSheet buttonText="숨기기" isOpen={isOpen} onClose={onClose}>
      <div className="flex flex-col gap-[16px]">
        <Typography size="headline-3" color="grey-600">
          조회수
        </Typography>
        <Typography
          size="body-2"
          color="blueGrey-800"
          className="flex items-center flex-wrap"
        >
          이 게시물이 조회된 횟수입니다. 자세히 알아보려면{' '}
          <NotSupportText
            text="고객센터"
            size="headline-8"
            color="black"
            className="underline"
          />
          를 알아보세요.
        </Typography>
      </div>
    </BottomSheet>
  );
}

export default ViewsBottomSheet;
