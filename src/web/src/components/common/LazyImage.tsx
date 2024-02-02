import { memo } from 'react';

import { cn } from '@/utils';
import { useLazyImage } from '@/hooks';

interface LazyImageProps {
  className?: string;
  src: string;
  alt: string;
  root?: IntersectionObserver['root'];
  rootMargin?: IntersectionObserver['rootMargin'];
  threshold?: number | number[];
  onLoadComplete?: VoidFunction;
  onInView?: VoidFunction;
}

function LazyImage({
  src,
  className,
  alt,
  root,
  rootMargin,
  threshold,
  onLoadComplete,
  onInView,
}: LazyImageProps) {
  const { ref, isLoading } = useLazyImage({
    src,
    options: { root, rootMargin, threshold, onInView, onLoadComplete },
  });

  if (isLoading) {
    return (
      <div className="w-full animate-pulse">
        <div
          className={cn(
            'w-full h-full bg-blueGrey-100 dark:bg-blueGrey-300',
            className,
          )}
        />
      </div>
    );
  }

  return (
    <img src={src} ref={ref} alt={alt} className={cn('w-full', className)} />
  );
}

const MemoizedLazyImage = memo(LazyImage);

export default MemoizedLazyImage;
