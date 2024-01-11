import { cloneElement } from 'react';
import { cva } from 'class-variance-authority';

import { ICONS } from './icons';
import type { ColorType } from '@/@types';
import { cn } from '@/utils';

const IconVariants = cva<{
  stroke: Record<ColorType, string>;
  fill: Record<ColorType, string>;
}>('', {
  variants: {
    stroke: {
      white: '[&>path]:stroke-white',
      'grey-100': '[&>path]:stroke-grey-100',
      'grey-200': '[&>path]:stroke-grey-200',
      'grey-300': '[&>path]:stroke-grey-300',
      'grey-400': '[&>path]:stroke-grey-400',
      'grey-500': '[&>path]:stroke-grey-500',
      'grey-600': '[&>path]:stroke-grey-600',
      black: '[&>path]:stroke-black',
      'blueGrey-100': '[&>path]:stroke-blueGrey-100',
      'blueGrey-200': '[&>path]:stroke-blueGrey-200',
      'blueGrey-300': '[&>path]:stroke-blueGrey-300',
      'blueGrey-400': '[&>path]:stroke-blueGrey-400',
      'blueGrey-500': '[&>path]:stroke-blueGrey-500',
      'blueGrey-600': '[&>path]:stroke-blueGrey-600',
      'blueGrey-700': '[&>path]:stroke-blueGrey-700',
      'green-100': '[&>path]:stroke-green-100',
      'green-200': '[&>path]:stroke-green-200',
      'purple-100': '[&>path]:stroke-purple-100',
      'purple-200': '[&>path]:stroke-purple-200',
      'red-100': '[&>path]:stroke-red-100',
      'red-200': '[&>path]:stroke-red-200',
      'yellow-100': '[&>path]:stroke-yellow-100',
      'blue-100': '[&>path]:stroke-blue-100',
      'blue-200': '[&>path]:stroke-blue-200',
      'blue-300': '[&>path]:stroke-blue-300',
      'blue-400': '[&>path]:stroke-blue-400',
      'blue-500': '[&>path]:stroke-blue-500',
    },
    fill: {
      white: '[&>path]:fill-white',
      'grey-100': '[&>path]:fill-grey-100',
      'grey-200': '[&>path]:fill-grey-200',
      'grey-300': '[&>path]:fill-grey-300',
      'grey-400': '[&>path]:fill-grey-400',
      'grey-500': '[&>path]:fill-grey-500',
      'grey-600': '[&>path]:fill-grey-600',
      black: '[&>path]:fill-black',
      'blueGrey-100': '[&>path]:fill-blueGrey-100',
      'blueGrey-200': '[&>path]:fill-blueGrey-200',
      'blueGrey-300': '[&>path]:fill-blueGrey-300',
      'blueGrey-400': '[&>path]:fill-blueGrey-400',
      'blueGrey-500': '[&>path]:fill-blueGrey-500',
      'blueGrey-600': '[&>path]:fill-blueGrey-600',
      'blueGrey-700': '[&>path]:fill-blueGrey-700',
      'green-100': '[&>path]:fill-green-100',
      'green-200': '[&>path]:fill-green-200',
      'purple-100': '[&>path]:fill-purple-100',
      'purple-200': '[&>path]:fill-purple-200',
      'red-100': '[&>path]:fill-red-100',
      'red-200': '[&>path]:fill-red-200',
      'yellow-100': '[&>path]:fill-yellow-100',
      'blue-100': '[&>path]:fill-blue-100',
      'blue-200': '[&>path]:fill-blue-200',
      'blue-300': '[&>path]:fill-blue-300',
      'blue-400': '[&>path]:fill-blue-400',
      'blue-500': '[&>path]:fill-blue-500',
    },
  },
});

export type IconKeyType = keyof typeof ICONS;
export interface IconProps extends React.HTMLAttributes<HTMLElement> {
  type: IconKeyType;
  fill?: ColorType;
  stroke?: ColorType;
}

function Icon({ type, className, fill, stroke, ...props }: IconProps) {
  const TargetIcon = ICONS[type];

  return cloneElement(<TargetIcon />, {
    className: cn(IconVariants({ fill, stroke }), className),
    ...props,
  });
}

export default Icon;
