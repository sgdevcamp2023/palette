import { generateLocalStorage } from '@/utils';
import { AuthenticationRequiredError } from './AuthenticationRequiredError';

export class AuthTokenStorage {
  private authToken: string | null;

  private storage: ReturnType<typeof generateLocalStorage<string | null>>;

  constructor(key: string) {
    this.spawn();
    this.storage = generateLocalStorage<string | null>(key);
    this.authToken = null;
  }

  spawn(): void {
    this.authToken = this.storage.get();
  }

  getToken(): AuthTokenStorage['authToken'] {
    return this.authToken;
  }

  getTokenOrThrow(): AuthTokenStorage['authToken'] {
    if (this.authToken == null) {
      throw new AuthenticationRequiredError();
    }
    return this.authToken;
  }

  clear(): void {
    this.authToken = null;
    this.storage.remove();
  }

  setToken(token: string): void {
    this.authToken = token;
    this.storage.set(token);
  }
}

export const accessTokenStorage = new AuthTokenStorage('@@@accessToken');
export const refreshTokenStorage = new AuthTokenStorage('@@@refreshToken');
