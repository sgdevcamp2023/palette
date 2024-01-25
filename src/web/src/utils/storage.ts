interface Storage<T> {
  get(): T | null;

  set(value: T): void;

  remove(): void;

  clear(): void;
}

interface StorageOption<T> {
  value?: T;
}

export class MemoStorage<T> implements Storage<T> {
  constructor(
    private readonly key: string,
    options: StorageOption<T> = {},
  ) {
    if (options.value != null && this.get() == null) {
      this.set(options.value);
    }
  }

  private storage = new Map<string, string>();

  public get(): T | null {
    const value = this.storage.get(this.key);
    return value ? this.deserialize(value) : null;
  }

  public set(value: T): void {
    this.storage.set(this.key, this.serialize(value));
  }

  public remove(): void {
    this.storage.delete(this.key);
  }

  public clear(): void {
    this.storage.clear();
  }

  private serialize(value: T): string {
    return JSON.stringify(value);
  }

  private deserialize(value: string): T | null {
    try {
      return JSON.parse(value);
    } catch (e) {
      return null;
    }
  }
}

function generateKey(): string {
  return Math.random().toString(36);
}

export class LocalStorage<T> implements Storage<T> {
  constructor(
    private readonly key: string,
    options: StorageOption<T> = {},
  ) {
    if (options.value != null && this.get() == null) {
      this.set(options.value);
    }
  }

  public static canUse(): boolean {
    const TEST_KEY = generateKey();

    // LocalStorage를 사용할 수 없는 경우에 대응합니다.
    try {
      window.localStorage.setItem(TEST_KEY, 'test');
      window.localStorage.removeItem(TEST_KEY);
      return true;
    } catch (err) {
      return false;
    }
  }

  public get(): T | null {
    const value = window.localStorage.getItem(this.key);
    return value ? this.deserialize(value) : null;
  }

  public set(value: T): void {
    window.localStorage.setItem(this.key, this.serialize(value));
  }

  public remove(): void {
    window.localStorage.removeItem(this.key);
  }

  public clear(): void {
    window.localStorage.clear();
  }

  private serialize(value: T): string {
    return JSON.stringify(value);
  }

  private deserialize(value: string): T | null {
    try {
      return JSON.parse(value);
    } catch (e) {
      return null;
    }
  }
}

export const generateLocalStorage = <T>(
  key: string,
  options?: StorageOption<T>,
): Storage<T> => {
  if (LocalStorage.canUse()) {
    return new LocalStorage<T>(key, options);
  }
  return new MemoStorage<T>(key, options);
};
