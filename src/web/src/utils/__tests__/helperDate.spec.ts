import { getDiffDate } from '..';

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
