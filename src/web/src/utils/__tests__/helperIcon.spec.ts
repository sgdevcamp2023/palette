import { iconOpacity } from '../helperIcon';

describe('iconOpacity', () => {
  it('should return opacity-95 if direction is up', () => {
    expect(iconOpacity('up')).toBe('opacity-95');
  });

  it('should return opacity-80 if direction is down', () => {
    expect(iconOpacity('down')).toBe('opacity-80');
  });

  it('should return empty string if direction is stop', () => {
    expect(iconOpacity('stop')).toBe('');
  });
});
