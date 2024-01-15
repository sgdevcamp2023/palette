import { useEffect, useState } from 'react';

type NotNullishValue = {};

function isDeeplyEqual<T extends NotNullishValue>(x: T, y: T) {
  return JSON.stringify(x) === JSON.stringify(y);
}

/**
 * comparator로 비교했을 때 값이 변경되었을 때에만 레퍼런스를 변경하도록 합니다.
 * @param value 레퍼런스를 보존할 값
 * @param isEqual 값의 동일성을 검증하는 함수
 * @returns value
 */
export const usePreservedReference = <T extends NotNullishValue>(
  value: T,
  isEqual: (a: T, b: T) => boolean = isDeeplyEqual,
) => {
  const [reference, setReference] = useState<T>(value);

  useEffect(() => {
    if (!isEqual(value, reference)) {
      setReference(value);
    }
  }, [isEqual, reference, value]);

  return reference;
};
