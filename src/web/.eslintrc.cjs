module.exports = {
  root: true,
  env: { browser: true, es2020: true },
  extends: ['airbnb', 'airbnb-typescript', 'plugin:@tanstack/eslint-plugin-query/recommended', 'prettier'],
  ignorePatterns: ['dist', '.eslintrc.cjs'],
  parser: '@typescript-eslint/parser',
  parserOptions: {
    ecmaVersion: 'latest',
    sourceType: 'module',
    project: './tsconfig.json',
    tsconfigRootDir: __dirname,
  },
  plugins: ['react-refresh'],
  rules: {
    'import/extensions': 'off', // import시 확장자는 가독성과 간결함을 위해 적지 않는다.
    'react/react-in-jsx-scope': 'off', // React 17 이상에서는 JSX 사용 시 React 임포트가 필요 없음. 이를 반영하여 규칙을 비활성화.
    'import/no-extraneous-dependencies': 'off', // 개발 의존성 및 일반 의존성 간 구분을 엄격하게 적용하지 않음. 프로젝트 요구에 유연하게 대응하기 위해.
    'import/prefer-default-export': 'off', // 1개의 named export를 할 수 있게 변경. 디렉토리로 컴포넌트를 구분하며 index.ts에는 한 개만 있을 가능성이 크기에
    'react/jsx-props-no-spreading': 'off', // spread 연산자를 사용할 수 있게 변경. props에서 주로 사용
    'react/require-default-props': 'off', // default props 대신 값이 없는 경우를 따로 처리할 수 있게 변경
    'react/no-unknown-property': ['error', { ignore: ['css'] }], // 공식문서에서 무시 명시 https://emotion.sh/docs/eslint-plugin-react
    'jsx-a11y/click-events-have-key-events': 'off', // onClick 이벤트 주입시 키보드 이벤트를 반드시 집어넣지 않는다.
    '@typescript-eslint/consistent-type-imports': [
      'error',
      {
        prefer: 'type-imports',
        disallowTypeAnnotations: false,
      },
    ], // import 시 type이면 type을 붙입니다.
    'react-refresh/only-export-components': [
      'warn',
      { allowConstantExport: true },
    ]
  },
};
