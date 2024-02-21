export class AuthenticationRequiredError extends Error {
  constructor() {
    super(`로그인이 필요한 서비스입니다`);
  }
}
