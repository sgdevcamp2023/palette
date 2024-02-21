import { isValidEmail } from '../isValidEmail';

describe('isValidEmail', () => {
  it('should return true if given valid email', () => {
    expect(isValidEmail('poiu694@naver.com')).toBe(true);
    expect(isValidEmail('poiu694@google.com')).toBe(true);
    expect(isValidEmail('poiu694@kakao.com')).toBe(true);
    expect(isValidEmail('poiu694@daum.net')).toBe(true);
    expect(isValidEmail('poiu694@uos.ac.kr')).toBe(true);
  });

  it('should return true if given email valid', () => {
    expect(isValidEmail('')).toBe(false);
    expect(isValidEmail('123')).toBe(false);
    expect(isValidEmail('poiu694')).toBe(false);
    expect(isValidEmail('poiu694@naver')).toBe(false);
    expect(isValidEmail('poiu694@naver.')).toBe(false);
    expect(isValidEmail('poiu694@google.123')).toBe(false);
    expect(isValidEmail('poiu694@google.com123')).toBe(false);
  });
});
