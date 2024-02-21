import { useMemo, useRef } from 'react';
import type { SyntheticEvent } from 'react';

interface LongPressOption {
  threshold: number;
  onStart?: (e: SyntheticEvent) => void;
  onFinish?: (e: SyntheticEvent) => void;
  onCancel?: (e: SyntheticEvent) => void;
}

const isTouchEvent = (event: SyntheticEvent) =>
  window.TouchEvent
    ? event.nativeEvent instanceof TouchEvent
    : 'touches' in event.nativeEvent;

const isMouseEvent = (event: SyntheticEvent) =>
  event.nativeEvent instanceof MouseEvent;

export const useLongPress = (
  callback: (e: SyntheticEvent) => void,
  options: LongPressOption = { threshold: 400 },
) => {
  const { threshold, onStart, onFinish, onCancel } = options;
  const isLongPressActive = useRef<boolean>(false);
  const isPressed = useRef<boolean>(false);
  const timerId = useRef<NodeJS.Timeout>();

  return useMemo(() => {
    if (typeof callback !== 'function') {
      return {};
    }

    const start = (event: SyntheticEvent) => {
      if (!isMouseEvent(event) && !isTouchEvent(event)) return;

      if (onStart) {
        onStart(event);
      }

      isPressed.current = true;
      timerId.current = setTimeout(() => {
        callback(event);
        isLongPressActive.current = true;
      }, threshold);
    };

    const cancel = (event: SyntheticEvent) => {
      if (!isMouseEvent(event) && !isTouchEvent(event)) return;

      if (isLongPressActive.current) {
        if (onFinish) {
          onFinish(event);
        }
      } else if (isPressed.current) {
        if (onCancel) {
          onCancel(event);
        }
      }

      isLongPressActive.current = false;
      isPressed.current = false;

      if (timerId.current) {
        window.clearTimeout(timerId.current);
      }
    };

    const mouseHandlers = {
      onMouseDown: start,
      onMouseUp: cancel,
      onMouseLeave: cancel,
    };

    const touchHandlers = {
      onTouchStart: start,
      onTouchEnd: cancel,
    };

    return {
      ...mouseHandlers,
      ...touchHandlers,
    };
  }, [callback, threshold, onCancel, onFinish, onStart]);
};
