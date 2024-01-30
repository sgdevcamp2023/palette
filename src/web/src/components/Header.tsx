import { memo, useEffect, useState } from 'react';
import { cva } from 'class-variance-authority';
import type { MouseEvent, MouseEventHandler, ReactNode } from 'react';

import { DUMMY_USER, cn } from '@/utils';
import MenuModal from './MenuModal';
import Typography from './common/Typography';
import type { IconKeyType } from './common/Icon';
import type { ColorType } from '@/@types';
import AccessibleIconButton from './AccessibleIconButton';

type HeaderType =
  | {
      type: IconKeyType;
      label: string;
      width?: number | string;
      height?: number | string;
      fill?: ColorType;
      stroke?: ColorType;
      className?: string;
      onClick?: MouseEventHandler<HTMLButtonElement>;
    }
  | {
      type: 'text';
      label: string;
      className?: string;
      onClick?: MouseEventHandler<HTMLButtonElement>;
    }
  | {
      type: 'node';
      onClick?: MouseEventHandler<HTMLButtonElement>;
      node: ReactNode;
    };

const HeaderButtonVariants = cva<{
  align: Record<'start' | 'mid' | 'end', string>;
}>('absolute', {
  variants: {
    align: {
      start: 'left-[14px]',
      mid: 'left-2/4 -translate-x-1/2',
      end: 'right-[14px]',
    },
  },
});

function HeaderButton({
  header,
  align,
}: {
  header?: HeaderType;
  align: 'start' | 'mid' | 'end';
}) {
  if (!header) {
    return <span className="w-[24px]" />;
  }

  return (
    <>
      {header.type !== 'text' && header.type !== 'node' && (
        <AccessibleIconButton
          width={header.width ?? 20}
          height={header.height ?? 20}
          iconType={header.type}
          fill={header.fill}
          stroke={header.stroke}
          onClick={header.onClick}
          label={header.label}
          className={cn(HeaderButtonVariants({ align }), header.className)}
        />
      )}
      {header.type === 'text' && (
        <button
          type="button"
          onClick={header.onClick}
          className={cn(HeaderButtonVariants({ align }), header.className)}
        >
          <Typography size={align === 'mid' ? 'headline-7' : 'body-2'}>
            {header.label}
          </Typography>
        </button>
      )}
      {header.type === 'node' && header.node}
    </>
  );
}

const HeaderVariants = cva<{
  position: Record<'sticky' | 'fixed', string>;
}>(
  'flex justify-between items-center px-[14px] w-[420px] max-w-full h-[44px] bg-white z-[999]',
  {
    variants: {
      position: {
        fixed: 'fixed',
        sticky: 'sticky',
      },
    },
    defaultVariants: {
      position: 'fixed',
    },
  },
);

interface HeaderProps {
  left?: HeaderType;
  center?: HeaderType;
  right?: HeaderType;
  className?: string;
  position?: 'sticky' | 'fixed';
}

function Header({ left, center, right, position, className }: HeaderProps) {
  const isProfile = left?.type === 'circlePerson';
  const [isProfileModalOpen, setIsProfileModalOpen] = useState<boolean>(false);

  const handleLeftHeaderButton = (
    e: MouseEvent<HTMLButtonElement, globalThis.MouseEvent>,
  ) => {
    if (isProfile) {
      setIsProfileModalOpen((prev) => !prev);
    }
    if (left?.type === 'node') return;
    left?.onClick?.(e);
  };

  useEffect(() => {
    if (isProfileModalOpen) {
      window.document.body.style.width = '100%';
      window.document.body.style.position = 'fixed';
      window.document.body.style.overflow = 'hidden';
    } else {
      window.document.body.style.width = 'auto';
      window.document.body.style.position = 'static';
    }
  }, [isProfileModalOpen]);

  return (
    <>
      <header className={cn(HeaderVariants({ position }), className)}>
        <HeaderButton
          header={
            left ? { ...left, onClick: handleLeftHeaderButton } : undefined
          }
          align="start"
        />
        <HeaderButton header={center} align="mid" />
        <HeaderButton header={right} align="end" />
      </header>
      {isProfile && isProfileModalOpen && (
        <MenuModal
          user={DUMMY_USER}
          onClose={() => setIsProfileModalOpen(false)}
        />
      )}
    </>
  );
}

const MemoizedHeader = memo(Header);

export default MemoizedHeader;
