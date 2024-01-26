import { countByte } from '..';

describe('countByte', () => {
  it('should 3byte when given korea', () => {
    expect(countByte('가')).toBe(3);
    expect(countByte('가나다라마바사아자차')).toBe(30);
    expect(countByte('각난닫랄맘밥삿앙잦찿')).toBe(30);
  });

  it('should 1byte when given english', () => {
    expect(countByte('a')).toBe(1);
    expect(countByte('abcdefghij')).toBe(10);
  });

  it('should 4byte when given emoji in UTF-8', () => {
    expect(countByte('😎')).toBe(4);
    expect(countByte('🔥')).toBe(4);
    expect(countByte('😭')).toBe(4);
    expect(countByte('😱')).toBe(4);
    expect(countByte('🥳')).toBe(4);
    expect(countByte('😢')).toBe(4);
    expect(countByte('🤗')).toBe(4);
    expect(countByte('😍')).toBe(4);
  });

  it('should specific byte when given other emoji', () => {
    expect(countByte('👨‍👨‍👧‍👦')).toBe(25);
    expect(countByte('👯‍♂️')).toBe(13);
    expect(countByte('☀️')).toBe(6);
    expect(countByte('‼️')).toBe(6);
    expect(countByte('#️⃣')).toBe(7);
  });
});
