import type { RefObject } from 'react';
import { useCallback, useEffect, useRef, useState } from 'react';

import { usePreservedCallback } from './usePreservedCallback';

const isIntersectionObserverSupported = () =>
  'IntersectionObserver' in window &&
  'IntersectionObserverEntry' in window &&
  'intersectionRatio' in window.IntersectionObserverEntry.prototype;

interface UseLazyImageProps {
  src: string;
  options: {
    root?: IntersectionObserver['root'];
    rootMargin?: IntersectionObserver['rootMargin'];
    threshold?: number | number[];
    onLoadComplete?: VoidFunction;
    onInView?: VoidFunction;
  };
}

const noop = () => {};

const isHTMLImageElement = ($element: Element): $element is HTMLImageElement =>
  $element instanceof HTMLImageElement;

export const useLazyImage = ({
  src,
  options: {
    rootMargin,
    threshold,
    root,
    onInView = noop,
    onLoadComplete = noop,
  },
}: UseLazyImageProps): {
  ref: RefObject<HTMLImageElement>;
  isLoading: boolean;
} => {
  const ref = useRef<HTMLImageElement>(null);
  const [isLoading, setIsLoading] = useState<boolean>(false);

  const onInViewEvent = usePreservedCallback(onInView ?? noop);
  const onLoadCompleteEvent = usePreservedCallback(onLoadComplete ?? noop);

  const registerLoadImageEvent = useCallback(
    ($image: HTMLImageElement) => {
      setIsLoading(true);

      $image.onload = () => {
        onLoadCompleteEvent();
        setIsLoading(false);
      };
    },
    [onLoadCompleteEvent],
  );

  const injectSrcOnImage = useCallback(
    ($image: HTMLImageElement) => {
      $image.src = src;

      if ($image.complete) {
        onLoadCompleteEvent();
        return;
      }
      registerLoadImageEvent($image);
    },
    [src, onLoadComplete, registerLoadImageEvent],
  );

  const intersectionAction = useCallback(
    ([entry]: IntersectionObserverEntry[], observer: IntersectionObserver) => {
      if (!entry || !entry.isIntersecting) return;

      const $target = entry.target;

      if (isHTMLImageElement($target)) {
        injectSrcOnImage($target);
        onInViewEvent();

        observer.unobserve($target);
      }
    },
    [injectSrcOnImage, onInViewEvent],
  );

  useEffect(() => {
    const $image = ref.current;

    if (!$image) return;

    if (!isIntersectionObserverSupported()) return;

    const observer = new IntersectionObserver(intersectionAction, {
      root,
      rootMargin,
      threshold,
    });

    observer.observe($image);

    // eslint-disable-next-line consistent-return
    return () => {
      observer.unobserve($image);
    };
  }, [
    root,
    threshold,
    rootMargin,
    registerLoadImageEvent,
    injectSrcOnImage,
    intersectionAction,
  ]);

  return {
    ref,
    isLoading,
  };
};
