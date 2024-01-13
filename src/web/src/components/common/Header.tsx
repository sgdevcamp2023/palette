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

function HeaderButton({
  header,
  isCenter,
}: {
  header?: HeaderType;
  isCenter?: boolean;
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
          className={header.className}
        />
      )}
      {header.type === 'text' && (
        <button
          type="button"
          onClick={header.onClick}
          className={header.className}
        >
          <Typography size={isCenter ? 'headline-7' : 'body-2'}>
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
  'flex justify-between items-center px-[14px] w-[420px] max-w-full h-[44px] border-b-[1px]',
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
      <HeaderButton header={left} />
      <HeaderButton header={center} isCenter />
      <HeaderButton header={right} />
    </header>
  );
}

export default Header;
