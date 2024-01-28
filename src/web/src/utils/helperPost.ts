import type { EditPaint } from '@/@types';

/**
 * 기본 값으로 채워주면서 EditPaint 타입으로 만들어줍니다.
 */
export const forEditPaint = (
  paint: Omit<Partial<EditPaint>, 'medias'>,
): Omit<EditPaint, 'medias'> => ({
  text: paint.text ?? '',
  taggedUserIds: paint.taggedUserIds ?? [],
  quotePaintId: paint.quotePaintId ?? '',
  inReplyToPaintId: paint.inReplyToPaintId ?? '',
  hashtags: paint.hashtags ?? [],
  mentions: paint.mentions ?? [],
  links: paint.links ?? [],
});

export const convertToMedia = (
  url: string,
  type: EditPaint['medias'][number]['type'],
): EditPaint['medias'][number] => ({
  id: url,
  type,
});

export const linkRegex =
  /(http(s)?:\/\/.)?(www\.)?[-a-zA-Z0-9@:%._+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_+.~#?&//=]*)/g;
