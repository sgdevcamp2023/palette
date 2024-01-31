import { forwardRef, memo } from 'react';
import type { ComponentProps, HTMLAttributes, ReactNode } from 'react';

import Button from './Button';
import { Modal } from './Modal';
import Typography from './Typography';

const BottomSheetModalHeader = forwardRef<
  HTMLDivElement,
  HTMLAttributes<HTMLDivElement>
>((props, ref) => (
  <div
    ref={ref}
    {...props}
    className="w-[36px] h-[6px] rounded-[4px] bg-grey-200 flex justify-center mt-1 mb-[14px]"
  />
));

interface BottomSheetModalProps {
  isOpen: ComponentProps<typeof Modal>['isOpen'];
  onClose: ComponentProps<typeof Modal>['onClose'];
  buttonText: string;
  children: ReactNode;
}

function BottomSheetModal({
  isOpen,
  onClose,
  buttonText,
  children,
}: BottomSheetModalProps) {
  return (
    <Modal isOpen={isOpen} onClose={onClose} closeOnOverlayClick>
      <div className="flex flex-col items-center z-[9999] fixed bottom-0 left-0 right-0 py-0 px-[22px] border-[1px] border-grey-300 rounded-t-[32px] bg-white animate-slide-up">
        <BottomSheetModalHeader />
        <div className="w-full flex flex-col gap-[30px] overflow-y-auto pb-[30px]">
          {children}
          <Button variant="outlined" onClick={onClose}>
            <Typography size="headline-7" color="grey-600">
              {buttonText}
            </Typography>
          </Button>
        </div>
      </div>
    </Modal>
  );
}

const MemoizedBottomSheetModal = memo(BottomSheetModal);

export default MemoizedBottomSheetModal;
