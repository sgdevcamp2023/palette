import type { Direction } from '@/@types';

export const iconOpacity = (direction: Direction) => {
  if (direction === 'up') return 'opacity-95';
  if (direction === 'down') return 'opacity-80';
  return '';
};
