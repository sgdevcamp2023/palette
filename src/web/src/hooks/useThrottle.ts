import { useEffect, useMemo } from 'react';
import throttle from 'lodash.throttle';

import { usePreservedCallback } from './usePreservedCallback';
import { usePreservedReference } from './usePreservedReference';

export const useThrottle = <F extends (...args: any[]) => any>(
  callback: F,
  wait: number,
  options: Parameters<typeof throttle>[2] = {},
) => {
  const preservedCallback = usePreservedCallback(callback);
  const preservedOptions = usePreservedReference(options);

  const throttled = useMemo(
    () => throttle(preservedCallback, wait, preservedOptions),
    [preservedCallback, preservedOptions, wait],
  );

  // eslint-disable-next-line arrow-body-style
  useEffect(() => {
    return () => {
      throttled.cancel();
    };
  }, [throttled]);

  return throttled;
};
