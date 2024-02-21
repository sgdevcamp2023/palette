# Palette Web

## 시작 방법

해당 프로젝트는 `pnpm` 패키지 매니저를 사용합니다.

pnpm이 강제가 아니므로, 필요에 따라 `npm,` `yarn` 을 통해 설치 및 실행도 가능합니다.

### pnpm 설치

[pnpm](https://pnpm.io/installation) 공식 문서에 따라 설치를 진행합니다.

### pnpm 사용 이유

[pnpm comparison](https://pnpm.io/feature-comparison) 에서 언급하는 것처럼 `Content-addressable storage` 를 통해 단순하게 파일 이름으로 해당 파일을 접근하는 것이 아니라 git 에서 커밋에 hash id 를 부여하듯 각각의 의존성 파일에 hash id 를 부여하고 관리합니다. 이 과정에서 중복되는 패키지는 동일한 hash id 를 얻게 되며 최적화를 진행합니다. 추후 모노레포로 갈 수 있는 `확장성` 을 도모하기 위해서 `pnpm`을 통해 관리하려고 합니다.

`pnpm` `v7` 부터는 `Side-effects cache`를 통해 빌드해야 하는 디펜던시가 시스템에서 한 번만 빌드됩니다. 빌드 스크립트와 종속성이 있는 프로젝트에서 설치 속도가 향상되며 위와 마찬가지로 `확장성`을 도모할 때 유용한 기능이라고 생각하며 `pnpm`을 사용하려고 합니다.

### 개발모드 시작

```bash
pnpm install # 의존성 설치하기
pnpm dev # dev 모드로 시작
```

[로컬 환경](http://localhost:5173)에 들어가 개발 환경을 볼 수 있습니다.

## 디렉터리 구조

```bash
🎨 PALETTE # PROJECT
 ┣ 🗂️ public # 정적인 파일 관리
 ┣ 🗂️ src
 ┃ ┣ 🗂️ @types # 공통 타입 인터페이스 정의
 ┃ ┣ 🗂️ api # api 와 관련되어 사용
 ┃ ┣ 🗂️ components # 페이지에서 사용되는 컴포넌트
 ┃ ┃ ┗ 🗂️ common # atom에 해당하는 디자인 시스템 컴포넌트
 ┃ ┣ 🗂️ constants # 정적 상수와 관련되어 사용
 ┃ ┣ 🗂️ hooks # 범용성을 위한 리액트 훅
 ┃ ┣ 🗂️ pages # PALETTE 내 사용되는 페이지
 ┃ ┣ 🗂️ routes # 페이지에 라우팅 관리
 ┃ ┣ 🗂️ utils # 리액트와 종속되지 않는 유틸 관리
 ┃ ┣ 📄 App.tsx
 ┃ ┣ 📄 index.css
 ┃ ┣ 📄 main.tsx
 ┃ ┗ 📄 vite-env.d.ts
```

### env

```text
VITE_BASE_SERVER_URL = ''
VITE_CDN_BASE_URL = ''
VITE_CLOUD_NAME = ''
VITE_CLD_API_KEY = ''
VITE_CLD_PRESET_NAME = ''
VITE_CLD_SECRET = ''
VITE_CLD_ENVIRONMENT_VARIABLE = ''
```
