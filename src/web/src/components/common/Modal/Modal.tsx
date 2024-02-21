import { useEffect, useMemo } from 'react';
import type { HTMLAttributes, ReactNode } from 'react';

import { cn } from '@/utils';
import Portal from './Portal';
import { ModalContext } from './ModalContext';

export interface ModalProps extends HTMLAttributes<HTMLDivElement> {
  isOpen: boolean;
  className?: string;
  closeOnOverlayClick?: boolean;
  children: ReactNode;
  onClose: VoidFunction;
}

function Modal({
  isOpen,
  children,
  className,
  closeOnOverlayClick = true,
  onClose,
  ...props
}: React.PropsWithChildren<ModalProps>) {
  const id = useMemo(() => String(new Date().getTime()), []);
  const modalProviderValue = useMemo(() => ({ isOpen, onClose }), []);

  useEffect(() => {
    if (isOpen) {
      window.document.body.style.width = '100%';
      window.document.body.style.position = 'fixed';
      window.document.body.style.overflow = 'hidden';
    } else {
      window.document.body.style.width = 'auto';
      window.document.body.style.position = 'static';
    }
  }, [isOpen]);

  const handleClickModalOverlay = () => {
    if (!closeOnOverlayClick) {
      return;
    }
    onClose();
  };

  if (!isOpen) {
    return null;
  }

  return (
    <ModalContext.Provider value={modalProviderValue} {...props}>
      <Portal id={id}>
        <div
          className={cn(
            'absolute left-0 bottom-0 flex justify-center items-center z-[9999]',
            className,
          )}
        >
          {children}
        </div>
        <div
          role="none"
          className="w-full h-full z-[9995] bg-[linear-gradient(rgba(0,0,0,0.5),rgba(0,0,0,0.5))]"
          onClick={handleClickModalOverlay}
        />
      </Portal>
    </ModalContext.Provider>
  );
}

export default Modal;
