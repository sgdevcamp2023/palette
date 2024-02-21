import { LocalStorage, MemoStorage, generateLocalStorage } from '../storage';

describe('typed-local-storage', () => {
  it('should return LocalStorage when can use localStorage', () => {
    const GENERATE_KEY = 'generate-key';
    expect(generateLocalStorage(GENERATE_KEY)).toBeInstanceOf(LocalStorage);
  });

  it('should return null when given no initial options', () => {
    const GET_KEY = 'get-empty-key';
    const storage = generateLocalStorage(GET_KEY);

    expect(storage.get()).toBe(null);
  });

  it('should return initialValue when given initial value', () => {
    const INITIAL_VALUE = {
      a: 1,
    };
    const GET_KEY = 'get-key';
    const storage = generateLocalStorage(GET_KEY, {
      value: INITIAL_VALUE,
    });

    expect(storage.get()).toStrictEqual(INITIAL_VALUE);
  });

  it('should return setting value when use set method', () => {
    const SET_KEY = 'set-key';
    const storage = generateLocalStorage(SET_KEY, {
      value: 'value',
    });
    storage.set('setting-value');

    expect(storage.get()).toBe('setting-value');
  });

  it('should return null when use clear method', () => {
    const CLEAR_KEY = 'clear-key';
    const storage = generateLocalStorage(CLEAR_KEY, {
      value: 'value',
    });
    storage.clear();

    expect(storage.get()).toBe(null);
  });

  it('should return null when use remove method', () => {
    const REMOVE_KEY = 'remove-key';
    const storage = generateLocalStorage(REMOVE_KEY, {
      value: 'value',
    });
    storage.remove();

    expect(storage.get()).toBe(null);
  });
});

describe('memo-storage', () => {
  it('should return null when given no initial options', () => {
    const GET_KEY = 'get-empty-key';
    const storage = new MemoStorage(GET_KEY);

    expect(storage.get()).toBe(null);
  });

  it('should return initialValue when given initial value', () => {
    const INITIAL_VALUE = {
      a: 1,
    };
    const GET_KEY = 'get-key';
    const storage = new MemoStorage(GET_KEY, {
      value: INITIAL_VALUE,
    });

    expect(storage.get()).toStrictEqual(INITIAL_VALUE);
  });

  it('should return setting value when use set method', () => {
    const SET_KEY = 'set-key';
    const storage = new MemoStorage(SET_KEY, {
      value: 'value',
    });
    storage.set('setting-value');

    expect(storage.get()).toBe('setting-value');
  });

  it('should return null when use clear method', () => {
    const CLEAR_KEY = 'clear-key';
    const storage = new MemoStorage(CLEAR_KEY, {
      value: 'value',
    });
    storage.clear();

    expect(storage.get()).toBe(null);
  });

  it('should return null when use remove method', () => {
    const REMOVE_KEY = 'remove-key';
    const storage = new MemoStorage(REMOVE_KEY, {
      value: 'value',
    });
    storage.remove();

    expect(storage.get()).toBe(null);
  });
});
