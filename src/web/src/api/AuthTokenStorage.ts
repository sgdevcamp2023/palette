import { generateLocalStorage } from '@/utils';
import { AuthenticationRequiredError } from './AuthenticationRequiredError';

export class AuthTokenStorage {
  private readonly authTokenKey = '@@@authToken';

  private readonly storage = generateLocalStorage<string | null>(
    this.authTokenKey,
  );

  private authToken: string | null;

  constructor() {
    this.spawn();
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

export const authTokenStorage = new AuthTokenStorage();
