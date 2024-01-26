import type { EditPaint } from '@/@types';

/**
 * 기본 값으로 채워주면서 EditPaint 타입으로 만들어줍니다.
 */
export const forEditPaint = (paint: Partial<EditPaint>): EditPaint => ({
  text: paint.text ?? '',
  medias: paint.medias ?? [],
  taggedUserIds: paint.taggedUserIds ?? [],
  quotePaintId: paint.quotePaintId ?? '',
  inReplyToPaintId: paint.inReplyToPaintId ?? '',
  hashtags: paint.hashtags ?? [],
  mentions: paint.mentions ?? [],
  links: paint.links ?? [],
});
