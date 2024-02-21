import type { User } from '@/@types';

export const DUMMY_USER: User = {
  id: '123',
  email: 'poiu694@naver.com',
  password: '',
  profileImagePath: 'profile/k3cvomo4mknrzsub83n7',
  backgroundImagePath: 'background/msqoll4kckvhw5gfgqgx',
  status: 'public',
  username: '@sangmin',
  nickname: '상민',
  introduce: '상민이의 소개 !',
  websitePath: 'https://github.com/poiu694',
  joinedAt: new Date().toISOString(),
  followerCount: 423,
  followingCount: 12,
};

export const createDummyUsers = (length: number): User[] =>
  Array.from({ length }).map((_, index) => ({
    ...DUMMY_USER,
    id: String(index),
    nickname: ['김도율', '김도현', '박희원', '이상민', '이원영'][index % 5],
    username: ['@doyul', '@dohyeon', '@heewon', '@sangmini', '@210'][index % 5],
    profileImagePath: [
      'profile/t1dyeoponhi213q45ilc',
      'profile/ydep7rd33mmmpu99c2wc',
      'profile/qxbdyyqmtubjuadeb6is',
      'profile/s76oz6lo7bdpwvkdymmz',
      'profile/lnminict8arehfeqwfd7',
    ][index % 5],
    introduce: [
      `그리고 채널이 추가적으로 필요하신 경우, 원하는 채널명과 채널이 필요한 이유를 담아 ⁠🗣ㅣ`,
      `디스코드에서 더 많은 소통이 일어나길 바라며 ㅎㅎ 오늘도 미리 수고 많으셨어요 🧡\n`,
      `아, 테크에 올린 줄 알았어요!\nㅋㅋㅋㅋ`,
      `제 동생 찐빵이에오.. 귀엽쬬... 7개월된 자브종 입니다...멍`,
      `여러분 이번주에는 개인별 업무 담당 영역, 아키텍쳐, 설계 등에 대해 얘기를 나눌 예정이에요!`,
    ][index % 5],
  }));
