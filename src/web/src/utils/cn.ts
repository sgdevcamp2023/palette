import { clsx, type ClassValue } from 'clsx';
import { extendTailwindMerge } from 'tailwind-merge';

export const extendTwMerge = extendTailwindMerge({
  extend: {
    classGroups: {
      'font-size': [
        'text-headline-1',
        'text-headline-2',
        'text-headline-3',
        'text-headline-4',
        'text-headline-5',
        'text-headline-6',
        'text-sub-headline-1',
        'text-body-1',
        'text-body-2',
        'text-body-3',
      ],
      'text-color': [
        'text-grey-100',
        'text-grey-200',
        'text-grey-300',
        'text-grey-400',
        'text-grey-500',
        'text-grey-600',
        'text-blueGrey-100',
        'text-blueGrey-200',
        'text-blueGrey-300',
        'text-blueGrey-400',
        'text-blueGrey-500',
        'text-blueGrey-600',
        'text-blueGrey-700',
        'text-green-100',
        'text-green-200',
        'text-purple-100',
        'text-purple-200',
        'text-red-100',
        'text-red-200',
        'text-yellow-100',
        'text-blue-100',
        'text-blue-200',
        'text-blue-300',
        'text-blue-400',
        'text-blue-500',
      ],
    },
  },
});

/**
 * className을 합쳐주는 유틸함수입니다.
 * @param inputs 합칠 className
 * @returns 합쳐진 className의 string
 */
export function cn(...inputs: ClassValue[]): string {
  return extendTwMerge(clsx(inputs));
}
