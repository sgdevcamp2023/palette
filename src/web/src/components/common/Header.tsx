import { cva } from 'class-variance-authority';
import type { MouseEventHandler } from 'react';

import { cn } from '@/utils';
import Typography from './Typography';
import type { IconKeyType } from './Icon';
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
      {header.type !== 'text' && (
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
    </>
  );
}

const HeaderVariants = cva<{
  position: Record<'sticky' | 'fixed', string>;
}>(
  'flex justify-between items-center px-[14px] w-[420px] max-w-full h-[44px]',
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
  return (
    <header className={cn(HeaderVariants({ position }), className)}>
      <HeaderButton header={left} align="start" />
      <HeaderButton header={center} align="mid" />
      <HeaderButton header={right} align="end" />
    </header>
  );
}

export default Header;
