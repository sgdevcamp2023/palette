import type { ElementType, HTMLAttributes } from 'react';
import { cva } from 'class-variance-authority';

import type { ColorType, FontKeyType } from '@/@types';
import { cn } from '@/utils';

export const TypographyVariants = cva<{
  size: Record<FontKeyType, string>;
  color: Record<ColorType, string>;
}>('', {
  variants: {
    size: {
      'headline-1': 'text-headline-1',
      'headline-2': 'text-headline-2',
      'headline-3': 'text-headline-3',
      'headline-4': 'text-headline-4',
      'headline-5': 'text-headline-5',
      'headline-6': 'text-headline-6',
      'headline-7': 'text-headline-7',
      'headline-8': 'text-headline-8',
      'sub-headline-1': 'text-sub-headline-1',
      'sub-headline-2': 'text-sub-headline-2',
      'sub-headline-3': 'text-sub-headline-3',
      'body-1': 'text-body-1',
      'body-2': 'text-body-2',
      'body-3': 'text-body-3',
      'body-4': 'text-body-4',
    },
    color: {
      white: 'text-white',
      'grey-100': 'text-grey-100',
      'grey-200': 'text-grey-200',
      'grey-300': 'text-grey-300',
      'grey-400': 'text-grey-400',
      'grey-500': 'text-grey-500',
      'grey-600': 'text-grey-600',
      black: 'text-black',
      'blueGrey-100': 'text-blueGrey-100',
      'blueGrey-200': 'text-blueGrey-200',
      'blueGrey-300': 'text-blueGrey-300',
      'blueGrey-400': 'text-blueGrey-400',
      'blueGrey-500': 'text-blueGrey-500',
      'blueGrey-600': 'text-blueGrey-600',
      'blueGrey-700': 'text-blueGrey-700',
      'blueGrey-800': 'text-blueGrey-800',
      'green-100': 'text-green-100',
      'green-200': 'text-green-200',
      'purple-100': 'text-purple-100',
      'purple-200': 'text-purple-200',
      'pink-100': 'text-pink-100',
      'pink-200': 'text-pink-200',
      'red-100': 'text-red-100',
      'yellow-100': 'text-yellow-100',
      'blue-100': 'text-blue-100',
      'blue-200': 'text-blue-200',
      'blue-300': 'text-blue-300',
      'blue-400': 'text-blue-400',
      'blue-500': 'text-blue-500',
    },
  },
});

interface TypographyProps extends HTMLAttributes<HTMLSpanElement> {
  size: FontKeyType;
  color?: ColorType;
  as?: ElementType;
}

function Typography({
  as,
  size,
  className,
  children,
  color = 'black',
  ...props
}: TypographyProps) {
  const Component = as || 'div';

  return (
    <Component
      className={cn(TypographyVariants({ size, color }), className)}
      {...props}
    >
      {children}
    </Component>
  );
}

export default Typography;
