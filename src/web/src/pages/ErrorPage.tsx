function ErrorPage(): JSX.Element {
  throw new Error('잘못된 페이지 접근입니다.');
}

export default ErrorPage;
