import type { EditPaint } from '@/@types';

type ValueWithIndex = { start: number; end: number; value: string };
export const TagMentionLinkRegex =
  /(http(s)?:\/\/.)?(www\.)?[-a-zA-Z0-9@:%._+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_+.~#?&//=]*)|@(\w+)|#(\w+)/g;
const extractTagMentionLink = (
  text?: string,
): Record<'links' | 'mentions' | 'hashtags', ValueWithIndex[]> => {
  const links: ValueWithIndex[] = [];
  const mentions: ValueWithIndex[] = [];
  const hashtags: ValueWithIndex[] = [];
  if (!text) {
    return {
      links,
      mentions,
      hashtags,
    };
  }

  const LIMIT_URL_LENGTH = 23;
  const matches = Array.from(text.matchAll(TagMentionLinkRegex));
  let delta = 0;
  for (const match of matches) {
    const value = match[0];
    const start = Number(match.index ?? 0);
    const end = start + value.length;

    if (value.startsWith('http')) {
      const linkEnd = Math.min(start + LIMIT_URL_LENGTH, end);
      links.push({ start: start - delta, end: linkEnd - delta, value });
      delta += end - linkEnd;
    } else if (value[0] === '#') {
      hashtags.push({
        start: start - delta,
        end: end - delta - 1,
        value: value.slice(1),
      });
    } else if (value[0] === '@') {
      mentions.push({
        start: start - delta,
        end: end - delta - 1,
        value: value.slice(1),
      });
    }
  }

  return {
    links,
    mentions,
    hashtags,
  };
};

const forLinkFromValue = (links: ValueWithIndex[]): EditPaint['links'] =>
  links.map((item) => ({
    start: item.start,
    end: item.end,
    link: item.value,
  }));

const forMentionFromValue = (
  mentions: ValueWithIndex[],
): EditPaint['mentions'] =>
  mentions.map((item) => ({
    start: item.start,
    end: item.end,
    mention: item.value,
    userId: '',
  }));

const forTagFromValue = (tags: ValueWithIndex[]): EditPaint['hashtags'] =>
  tags.map((item) => ({
    start: item.start,
    end: item.end,
    tag: item.value,
  }));

/**
 * 기본 값으로 채워주면서 EditPaint 타입으로 만들어줍니다.
 */
export const forEditPaint = (paint: Partial<EditPaint>): EditPaint => {
  const { links, mentions, hashtags } = extractTagMentionLink(paint.text);

  return {
    text: paint.text ?? '',
    taggedUserIds: paint.taggedUserIds ?? [],
    quotePaintId: paint.quotePaintId ?? '',
    inReplyToPaintId: paint.inReplyToPaintId ?? '',
    links: forLinkFromValue(links),
    mentions: forMentionFromValue(mentions),
    hashtags: forTagFromValue(hashtags),
    medias: paint.medias ?? [],
  };
};

export const convertToMedia = (
  url: string,
  type: EditPaint['medias'][number]['type'],
): EditPaint['medias'][number] => ({
  id: url,
  type,
});
