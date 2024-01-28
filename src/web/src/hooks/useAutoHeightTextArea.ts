import { useEffect, useRef } from 'react';
import type { MutableRefObject } from 'react';

const DEFAULT_MAX_HEIGHT = 264;

export const useAutoHeightTextArea = (
  text: string,
  options: {
    maxHeight: number;
  } = { maxHeight: DEFAULT_MAX_HEIGHT },
): MutableRefObject<HTMLTextAreaElement> => {
  const ref = useRef<HTMLTextAreaElement | null>(null);

  useEffect(() => {
    if (!ref.current) return;

    ref.current.style.height = '0px';
    const { scrollHeight } = ref.current;
    ref.current.style.height = `${Math.min(scrollHeight, options.maxHeight)}px`;
  }, [text]);

  return ref as MutableRefObject<HTMLTextAreaElement>;
};
