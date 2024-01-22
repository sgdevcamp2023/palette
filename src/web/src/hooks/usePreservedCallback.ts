import { useCallback, useEffect, useRef } from 'react';

/**
 * 컴포넌트가 mount 되어 있는 동안, callback 함수의 레퍼런스를 보존합니다.
 * 주어진 callback을 useRef 로 감싸서 레퍼런스를 보존합니다.
 * 
 * @param callback 레퍼런스를 보존할 함수
 * @returns callback
 */
export const usePreservedCallback = <Callback extends (...args: any[]) => any>(
  callback: Callback,
) => {
  const callbackRef = useRef<Callback>(callback);

  useEffect(() => {
    callbackRef.current = callback;
  }, [callback]);

  return useCallback(
    (...args: any[]) => callbackRef.current(...args),
    [callbackRef],
  ) as Callback;
};
