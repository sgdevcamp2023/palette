import type { User } from './auth';

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
    users: Pick<User, 'id' | 'nickname' | 'username'>[];
  };
}

export interface EditPaint {
  text: string;
  medias: {
    id: string;
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
