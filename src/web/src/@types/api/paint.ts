import type { User } from './auth';

export interface TimelineItem {
  id: string;
  isReply: boolean;
  authorId: User['id'];
  authorUsername: User['username'];
  authorNickname: User['nickname'];
  authorImagePath: User['profileImagePath'];
  authorStatus: User['status'];
  createdAt: string;
  text: string;
  replyCount: number;
  repaintCount: number;
  likeCount: number;
  like: boolean;
  repainted: boolean;
  marked: boolean;
  views: number;
  quotePaint: TimelineItem | null;
  entities: {
    hashtags: { start: number; end: number; tag: string }[];
    mentions: { start: number; end: number; mention: string; userId: string }[];
  };
  includes: {
    medias: { type: 'image' | 'video'; path: string }[];
    users: (Pick<User, 'id' | 'nickname' | 'username' | 'status'> & {
      imagePath: User['profileImagePath'];
    })[];
    links: {
      start: number;
      end: number;
      shortLink: string;
      originalLink: string;
    }[];
  };
}

export interface EditPaint {
  text: string;
  medias: {
    path: string;
    type: 'image' | 'video';
  }[];
  taggedUserIds: string[];
  quotePaintId: string;
  inReplyToPaintId: string;
  hashtags: {
    start: number;
    end: number;
    tag: string;
  }[];
  mentions: {
    start: number;
    end: number;
    userId: User['id'];
    mention: string;
  }[];
  links: {
    start: number;
    end: number;
    link: string;
  }[];
}
