import type { TimelineItem } from '@/@types';

function getRandomAdjustedDate(): Date {
  const currentDate = new Date();
  const timeOffset = Math.floor(Math.random() * 1000 * 60 * 60 * 24); // 1일은 86,400,000 밀리초

  const adjustedDate = new Date(currentDate.getTime() - timeOffset);

  return adjustedDate;
}

const DUMMY_ITEM: TimelineItem = {
  id: '12',
  isReply: false,
  authorId: '',
  authorUsername: '@sangmin',
  authorNickname: '이상민',
  authorImagePath:
    'https://pbs.twimg.com/profile_images/1734036193585893376/BkzwxOn2_400x400.png',
  authorStatus: 'public',
  createdAt: new Date(),
  text: `안녕하세요, @2023 개발캠프 여러분!\n지난 주에 이어서 오늘은 리사이클 팀의 현우 님(React-Query), 규민 님(상태관리)의 세미나가 진행됩니다.\n점심 식사하시고 1시 30분에 미팅룸 6번에서 만나요`,
  replyCount: 123,
  repaintCount: 34,
  likeCount: 43,
  views: 1253,
  like: true,
  marked: true,
  repainted: true,
  entities: {
    hashtags: [{ start: 0, end: 2, tag: 'tag' }],
    mentions: [{ start: 5, end: 7, mention: 'lee', userId: '12' }],
  },
  includes: {
    medias: [
      {
        type: 'image',
        path: 'https://pbs.twimg.com/media/GEFlR__bMAAr2MA?format=jpg&name=small',
      },
    ],
    paint: null,
    users: [
      {
        id: '123',
        nickname: '상민',
        username: '@sangmin',
        createdAt: new Date(),
      },
    ],
  },
};

export const createDummyTimelineItem = (length: number): TimelineItem[] =>
  Array.from({ length }).map((_, index) => ({
    ...DUMMY_ITEM,
    id: String(index),
    authorNickname: ['김도율', '김도현', '박희원', '이상민', '이원영'][
      index % 5
    ],
    authorUsername: ['@doyul', '@dohyeon', '@heewon', '@sangmini', '@210'][
      index % 5
    ],
    authorImagePath: [
      'https://avatars.githubusercontent.com/u/51396905?s=96&v=4',
      'https://avatars.githubusercontent.com/u/60564431?s=96&v=4',
      'https://avatars.githubusercontent.com/u/80496838?s=96&v=4',
      'https://avatars.githubusercontent.com/u/43488305?s=40&v=4',
      'https://avatars.githubusercontent.com/u/74983448?s=96&v=4',
    ][index % 5],
    replyCount: Math.floor(Math.random() * 1000),
    repaintCount: Math.floor(Math.random() * 1000),
    likeCount: Math.floor(Math.random() * 1000),
    views: Math.floor(Math.random() * 5000),
    like: index % 2 !== 0,
    marked: index % 2 === 0,
    repainted: index % 3 === 0,
    createdAt: getRandomAdjustedDate(),
    text: [
      `캠프가 진행되면서 디스코드 통해서 서로 유의미한 정보도 공유하고, 대화하는 모습이 아주 보기 좋습니다 (엄마미소) 🥰\n본 채널에 정보가 섞이는 것 같아서 채널을 분리해 보았어요.\n앞으로 공지 드리는 내용 놓치지 않도록! 정보 공유가 더 원활할 수 있도록!\n아래와 같이 채널을 활용해 주세요.`,
      `안녕하세요, @2023 개발캠프 여러분!\n지난 주에 이어서 오늘은 리사이클 팀의 현우 님(React-Query), 규민 님(상태관리)의 세미나가 진행됩니다.\n점심 식사하시고 1시 30분에 미팅룸 6번에서 만나요`,
      `⁠📣ㅣ2023-개발캠프-공지 캠프장님과 운영진의 공지를 확인하는 채널\n⁠🗣ㅣsmalltalk 아무얘기 자유롭게 나누는 채널\n⁠🔍ㅣ테크-공유 공유하고 싶은 기술정보 남기는 채널`,
      `그리고 채널이 추가적으로 필요하신 경우, 원하는 채널명과 채널이 필요한 이유를 담아 ⁠🗣ㅣsmalltalk 에 요청주시면\n운영진 확인 + 개발캠프 멤버의 좋아요👍  5개 받으면 생성해 드릴게요!`,
      `디스코드에서 더 많은 소통이 일어나길 바라며 ㅎㅎ 오늘도 미리 수고 많으셨어요 🧡\n`,
      `코드리뷰때 캠프장님이 얘기해주신 패키기 구조 가이드입니다.\nhttps://cheese10yun.github.io/spring-guide-directory/`,
      `설계 관련 고민을 하면서 재밌는 글을 찾았다.\n바로 **[ 서비스 엣지에서의 인증과 토큰에 구애 받지 않는 id 전파 ]** 라는 주제의 넷플릭스 기술 블로그 글이다.\n2021년 글이지만 관련 레퍼런스가 많이 없어서 아직 유용한 포스트 같다.`,
      `아, 테크에 올린 줄 알았어요!\nㅋㅋㅋㅋ`,
      `제 동생 찐빵이에오.. 귀엽쬬... 7개월된 자브종 입니다...멍`,
      `여러분 이번주에는 개인별 업무 담당 영역, 아키텍쳐, 설계 등에 대해 얘기를 나눌 예정이에요!\n개인별이지만 팀 단위로 만날거구요.`,
    ][index % 10],
    includes: {
      ...DUMMY_ITEM.includes,
      medias:
        Math.random() < 0.5
          ? [
              [],
              [
                {
                  type: 'image',
                  path: 'https://pbs.twimg.com/media/GEFlR__bMAAr2MA?format=jpg&name=small',
                },
              ] as TimelineItem['includes']['medias'],
              [
                {
                  type: 'image',
                  path: 'https://pbs.twimg.com/media/GBCjAhlbQAALcOl?format=jpg&name=small',
                },
              ] as TimelineItem['includes']['medias'],
              [
                {
                  type: 'image',
                  path: 'https://pbs.twimg.com/media/GDEGSwna4AAm-gj?format=jpg&name=small',
                },
              ] as TimelineItem['includes']['medias'],
              [
                {
                  type: 'image',
                  path: 'https://pbs.twimg.com/media/GDDX4BjbAAAR1qX?format=jpg&name=small',
                },
              ] as TimelineItem['includes']['medias'],
              [
                {
                  type: 'image',
                  path: 'https://pbs.twimg.com/media/GC-e1lPb0AAGro0?format=jpg&name=small',
                },
              ] as TimelineItem['includes']['medias'],
            ][index % 5]
          : [],
    },
  }));