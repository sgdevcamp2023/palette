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
      white: '[&_path]:stroke-white',
      'grey-100': '[&_path]:stroke-grey-100',
      'grey-200': '[&_path]:stroke-grey-200',
      'grey-300': '[&_path]:stroke-grey-300',
      'grey-400': '[&_path]:stroke-grey-400',
      'grey-500': '[&_path]:stroke-grey-500',
      'grey-600': '[&_path]:stroke-grey-600',
      black: '[&_path]:stroke-black',
      'blueGrey-100': '[&_path]:stroke-blueGrey-100',
      'blueGrey-200': '[&_path]:stroke-blueGrey-200',
      'blueGrey-300': '[&_path]:stroke-blueGrey-300',
      'blueGrey-400': '[&_path]:stroke-blueGrey-400',
      'blueGrey-500': '[&_path]:stroke-blueGrey-500',
      'blueGrey-600': '[&_path]:stroke-blueGrey-600',
      'blueGrey-700': '[&_path]:stroke-blueGrey-700',
      'blueGrey-800': '[&_path]:stroke-blueGrey-800',
      'green-100': '[&_path]:stroke-green-100',
      'green-200': '[&_path]:stroke-green-200',
      'purple-100': '[&_path]:stroke-purple-100',
      'purple-200': '[&_path]:stroke-purple-200',
      'pink-100': '[&_path]:stroke-pink-100',
      'pink-200': '[&_path]:stroke-pink-200',
      'red-100': '[&_path]:stroke-red-100',
      'yellow-100': '[&_path]:stroke-yellow-100',
      'blue-100': '[&_path]:stroke-blue-100',
      'blue-200': '[&_path]:stroke-blue-200',
      'blue-300': '[&_path]:stroke-blue-300',
      'blue-400': '[&_path]:stroke-blue-400',
      'blue-500': '[&_path]:stroke-blue-500',
    },
    fill: {
      white: '[&_path]:fill-white',
      'grey-100': '[&_path]:fill-grey-100',
      'grey-200': '[&_path]:fill-grey-200',
      'grey-300': '[&_path]:fill-grey-300',
      'grey-400': '[&_path]:fill-grey-400',
      'grey-500': '[&_path]:fill-grey-500',
      'grey-600': '[&_path]:fill-grey-600',
      black: '[&_path]:fill-black',
      'blueGrey-100': '[&_path]:fill-blueGrey-100',
      'blueGrey-200': '[&_path]:fill-blueGrey-200',
      'blueGrey-300': '[&_path]:fill-blueGrey-300',
      'blueGrey-400': '[&_path]:fill-blueGrey-400',
      'blueGrey-500': '[&_path]:fill-blueGrey-500',
      'blueGrey-600': '[&_path]:fill-blueGrey-600',
      'blueGrey-700': '[&_path]:fill-blueGrey-700',
      'blueGrey-800': '[&_path]:fill-blueGrey-800',
      'green-100': '[&_path]:fill-green-100',
      'green-200': '[&_path]:fill-green-200',
      'purple-100': '[&_path]:fill-purple-100',
      'purple-200': '[&_path]:fill-purple-200',
      'pink-100': '[&_path]:fill-pink-100',
      'pink-200': '[&_path]:fill-pink-200',
      'red-100': '[&_path]:fill-red-100',
      'yellow-100': '[&_path]:fill-yellow-100',
      'blue-100': '[&_path]:fill-blue-100',
      'blue-200': '[&_path]:fill-blue-200',
      'blue-300': '[&_path]:fill-blue-300',
      'blue-400': '[&_path]:fill-blue-400',
      'blue-500': '[&_path]:fill-blue-500',
    },
  },
});

export type IconKeyType = keyof typeof ICONS;
export interface IconProps extends React.HTMLAttributes<HTMLElement> {
  type: IconKeyType;
  width?: number | string;
  height?: number | string;
  fill?: ColorType;
  stroke?: ColorType;
}

function Icon({
  type,
  width,
  height,
  className,
  fill,
  stroke,
  ...props
}: IconProps) {
  const TargetIcon = ICONS[type];

  return cloneElement(
    <TargetIcon
      width={typeof width === 'number' ? `${width}px` : width}
      height={typeof height === 'number' ? `${height}px` : height}
    />,
    {
      className: cn(IconVariants({ fill, stroke }), className),
      ...props,
    },
  );
}

export default Icon;
