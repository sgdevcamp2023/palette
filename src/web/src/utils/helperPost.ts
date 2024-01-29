import type { EditPaint } from '@/@types';

const extractHashtags = (text?: string): EditPaint['hashtags'][number][] => {
  if (!text) return [];

  const regex = /#(\w+)/g;
  const matches: EditPaint['hashtags'][number][] = [];
  let match;

  match = regex.exec(text);
  while (match !== null) {
    const hashtag = match[1];
    const start = match.index;
    const end = start + hashtag.length;

    matches.push({ tag: hashtag, start, end });
    match = regex.exec(text);
  }

  return matches;
};

const extractMention = (text?: string): EditPaint['mentions'][number][] => {
  if (!text) return [];

  const regex = /@(\w+)/g;
  const matches: EditPaint['mentions'][number][] = [];
  let match;

  match = regex.exec(text);
  while (match !== null) {
    const mention = match[1];
    const start = match.index;
    const end = start + mention.length;

    matches.push({ mention, start, end, userId: '' });
    match = regex.exec(text);
  }

  return matches;
};

const extractLink = (text?: string): EditPaint['links'][number][] => {
  if (!text) return [];

  const regex =
    /(http(s)?:\/\/.)?(www\.)?[-a-zA-Z0-9@:%._+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_+.~#?&//=]*)/g;
  const matches: EditPaint['links'][number][] = [];
  let match;

  match = regex.exec(text);
  while (match !== null) {
    const link = match[0];
    const start = match.index;
    const end = start + link.length;

    matches.push({ link, start, end });
    match = regex.exec(text);
  }

  return matches;
};

/**
 * 기본 값으로 채워주면서 EditPaint 타입으로 만들어줍니다.
 */
export const forEditPaint = (paint: Partial<EditPaint>): EditPaint => ({
  text: paint.text ?? '',
  taggedUserIds: paint.taggedUserIds ?? [],
  quotePaintId: paint.quotePaintId ?? '',
  inReplyToPaintId: paint.inReplyToPaintId ?? '',
  hashtags: extractHashtags(paint.text),
  mentions: extractMention(paint.text),
  links: extractLink(paint.text),
  medias: paint.medias ?? [],
});

export const convertToMedia = (
  url: string,
  type: EditPaint['medias'][number]['type'],
): EditPaint['medias'][number] => ({
  id: url,
  type,
});
