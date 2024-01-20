export interface DiffDate {
  day: number;
  hour: number;
  minute: number;
  second: number;
}

/**
 * 두 개의 Date가 가리키는 시간의 차이를 계산합니다.
 * 계산하는 시간의 차이는 day, hour, minute, second로 나타내집니다.
 * startDate보다 endDate의 시간이 더 이르다면 모두 0을 반환합니다.
 * @param startDate 시작 시간
 * @param endDate 끝나는 시간
 * @returns DiffDate
 */
export const getDiffDate = (startDate: Date, endDate: Date): DiffDate => {
  const SECOND_MS = 1000;
  const MINUTE_MS = SECOND_MS * 60;
  const HOUR_MS = MINUTE_MS * 60;
  const DAY_MS = HOUR_MS * 24;

  const startTime = startDate.getTime();
  const endTime = endDate.getTime();
  const diff = endTime - startTime;
  if (diff <= 0) {
    return {
      day: 0,
      hour: 0,
      minute: 0,
      second: 0,
    };
  }

  return {
    day: Math.floor(diff / DAY_MS),
    hour: Math.floor((diff % DAY_MS) / HOUR_MS),
    minute: Math.floor((diff % HOUR_MS) / MINUTE_MS),
    second: Math.floor((diff % MINUTE_MS) / SECOND_MS),
  };
};
