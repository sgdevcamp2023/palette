import type { ComponentProps } from 'react';

import { BottomSheet, Icon, Typography } from '../common';

interface EditPostCancelBottomSheetProps {
  isOpen: ComponentProps<typeof BottomSheet>['isOpen'];
  onClose: ComponentProps<typeof BottomSheet>['onClose'];
  onClickDelete: VoidFunction;
  onClickSave: VoidFunction;
}

function EditPostCancelBottomSheet({
  isOpen,
  onClose,
  onClickDelete,
  onClickSave,
}: EditPostCancelBottomSheetProps) {
  return (
    <BottomSheet buttonText="취소" isOpen={isOpen} onClose={onClose}>
      <div className="flex flex-col gap-[32px]">
        <button
          type="button"
          className="flex gap-[18px] items-center"
          onClick={onClickDelete}
        >
          <Icon type="trash" width={20} height={20} />
          <Typography role="button" size="body-1" color="red-100">
            삭제
          </Typography>
        </button>
        <button
          type="button"
          className="flex gap-[18px] items-center"
          onClick={onClickSave}
        >
          <Icon type="pen" width={20} height={20} />
          <Typography role="button" size="body-1" color="grey-600">
            임시 저장
          </Typography>
        </button>
      </div>
    </BottomSheet>
  );
}

export default EditPostCancelBottomSheet;
