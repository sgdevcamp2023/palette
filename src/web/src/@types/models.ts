export interface User {
  id: string;
  email: string;
  profileImagePath: string;
  backgroundImagePath: string;
  status: 'private' | 'public';
  username: string;
  nickname: string;
  introduce: string;
  websitePath: string;
  createdAt: Date;
  followers: number;
  followings: number;
}

export interface TimelineItem {
  id: string;
  isReply: boolean;
  authorId: User['id'];
  authorUsername: User['username'];
  authorNickname: User['nickname'];
  authorImagePath: User['profileImagePath'];
  authorStatus: User['status'];
  createdAt: Date;
  text: string;
  replyCount: number;
  repaintCount: number;
  likeCount: number;
  views: number;
  like: boolean;
  repainted: boolean;
  marked: boolean;
  entities: {
    hashtags: { start: number; end: number; tag: string }[];
    mentions: { start: number; end: number; mention: string; userId: string }[];
  };
  includes: {
    medias: { type: 'image' | 'video'; path: string }[];
    paint: TimelineItem | null;
    users: Pick<User, 'id' | 'nickname' | 'username' | 'createdAt'>[];
  };
}
