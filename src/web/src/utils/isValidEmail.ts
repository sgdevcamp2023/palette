/**
 *
 * RFC 5322
 * 이메일이 유효한 포맷인지 검사하는 유틸 함수입니다.
 * @reference https://emailregex.com
 * @param email 유효성을 검사할 이메일
 * @returns 해당 이메일이 유효한지 (boolean)
 */
export const isValidEmail = (email: string) => {
  const regex =
    /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

  return regex.test(String(email).toLowerCase());
};
