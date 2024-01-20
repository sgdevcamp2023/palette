import { DateOrderError, getDiffDate, getDiffDateText } from '..';

describe('getDiffDate', () => {
  it('should return diff between two Dates with {day, hour, minute, second} format', () => {
    const startDate = new Date('2024-01-20 09:10:00');
    const endDate = new Date('2024-01-21 10:11:01');

    expect(getDiffDate(startDate, endDate)).toStrictEqual({
      day: 1,
      hour: 1,
      minute: 1,
      second: 1,
    });
  });

  it('should return diff between two Dates with {day, hour, minute, second} format', () => {
    const startDate = new Date('2024-01-20 09:00:59');
    const endDate = new Date('2024-01-20 10:00:00');

    expect(getDiffDate(startDate, endDate)).toStrictEqual({
      day: 0,
      hour: 0,
      minute: 59,
      second: 1,
    });
  });

  it('should return all property 0 if endDate < startDate', () => {
    const startDate = new Date('2024-01-20 09:10:00');
    const endDate = new Date('2024-01-19 10:11:01');

    expect(getDiffDate(startDate, endDate)).toStrictEqual({
      day: 0,
      hour: 0,
      minute: 0,
      second: 0,
    });
  });
});

describe('getDiffText', () => {
  it('should return day format when diffDate.day > 0', () => {
    const startDate = new Date('2024-01-20 09:10:00');
    const endDate = new Date('2024-01-21 10:11:01');

    expect(getDiffDateText(startDate, endDate)).toBe('1일');
  });

  it('should return hour format when diff day is 0, hour > 0', () => {
    const startDate = new Date('2024-01-20 09:10:00');
    const endDate = new Date('2024-01-20 10:11:01');

    expect(getDiffDateText(startDate, endDate)).toBe('1시간');
  });

  it('should return minute format when diff day, hour is 0, minute > 0', () => {
    const startDate = new Date('2024-01-20 09:10:00');
    const endDate = new Date('2024-01-20 09:11:01');

    expect(getDiffDateText(startDate, endDate)).toBe('1분');
  });

  it('should return second format when diff day, hour, minute is 0, second > 0', () => {
    const startDate = new Date('2024-01-20 09:11:00');
    const endDate = new Date('2024-01-20 09:11:01');

    expect(getDiffDateText(startDate, endDate)).toBe('1초');
  });

  it('should return `방금 전` format when startDate equals endDate', () => {
    const startDate = new Date('2024-01-20 09:11:00');
    const endDate = new Date('2024-01-20 09:11:00');

    expect(getDiffDateText(startDate, endDate)).toBe('방금 전');
  });

  it('should throw Error when startDate > endDate', () => {
    const startDate = new Date('2024-01-20 09:11:01');
    const endDate = new Date('2024-01-20 09:11:00');

    expect(() => {
      getDiffDateText(startDate, endDate);
    }).toThrow(DateOrderError);
  });
});
