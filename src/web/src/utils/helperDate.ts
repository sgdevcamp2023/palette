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

export class DateOrderError extends Error {
  constructor() {
    super('인자의 순서가 잘못되었습니다.(endDate < startDate)');
  }
}

/**
 * 두 개의 Date가 가리키는 시간의 차이를 계산하여 텍스트로 보여줍니다.
 * 게시글이 언제 생성되었는지 등에 사용되며 day, hour, minute, second 중 제일 큰 단위만 나오게 만듭니다.
 * 예외 1) 두 시간이 같은 경우라면 "방금 전" 이라는 텍스트로 대체해 리턴합니다.
 * 예외 2) endDate가 startDate보다 이른 경우 에러를 던집니다.
 * @param startDate 시작 시간
 * @param endDate 끝나는 시간
 * @returns 시간의 차이 문자열
 */
export const getDiffDateText = (startDate: Date, endDate: Date): string => {
  const diffDate = getDiffDate(startDate, endDate);

  if (endDate.getTime() < startDate.getTime()) {
    throw new DateOrderError();
  }

  if (diffDate.day > 0) {
    return `${diffDate.day}일`;
  }
  if (diffDate.hour > 0) {
    return `${diffDate.hour}시간`;
  }
  if (diffDate.minute > 0) {
    return `${diffDate.minute}분`;
  }
  if (diffDate.second > 0) {
    return `${diffDate.second}초`;
  }
  return '방금 전';
};
