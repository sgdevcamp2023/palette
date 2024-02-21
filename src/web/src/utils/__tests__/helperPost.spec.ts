import { convertToMedia, forEditPaint } from '..';

describe('forEditPaint', () => {
  it('should default value if given null', () => {
    expect(forEditPaint({})).toStrictEqual({
      text: '',
      taggedUserIds: [],
      quotePaintId: '',
      inReplyToPaintId: '',
      hashtags: [],
      mentions: [],
      links: [],
      medias: [],
    });
  });

  it('should media image value if given image', () => {
    expect(
      forEditPaint({
        medias: [convertToMedia('https://www.naver.com', 'image')],
      }),
    ).toStrictEqual({
      text: '',
      taggedUserIds: [],
      quotePaintId: '',
      inReplyToPaintId: '',
      hashtags: [],
      mentions: [],
      links: [],
      medias: [{ type: 'image', path: 'https://www.naver.com' }],
    });
  });

  it('should extract hashtag value in text', () => {
    expect(
      forEditPaint({
        text: '#abc #bcd hello world',
      }),
    ).toStrictEqual({
      text: '#abc #bcd hello world',
      taggedUserIds: [],
      quotePaintId: '',
      inReplyToPaintId: '',
      hashtags: [
        { tag: 'abc', start: 0, end: 3 },
        { tag: 'bcd', start: 5, end: 8 },
      ],
      mentions: [],
      links: [],
      medias: [],
    });
  });

  it('should extract mention value in text', () => {
    expect(
      forEditPaint({
        text: '@abc @bcd hello world',
      }),
    ).toStrictEqual({
      text: '@abc @bcd hello world',
      taggedUserIds: [],
      quotePaintId: '',
      inReplyToPaintId: '',
      hashtags: [],
      mentions: [
        { mention: 'abc', start: 0, end: 3, userId: '' },
        { mention: 'bcd', start: 5, end: 8, userId: '' },
      ],
      links: [],
      medias: [],
    });
  });

  it('should extract link value in text', () => {
    expect(
      forEditPaint({
        text: 'hello world https://www.naver.com should',
      }),
    ).toStrictEqual({
      text: 'hello world https://www.naver.com should',
      taggedUserIds: [],
      quotePaintId: '',
      inReplyToPaintId: '',
      hashtags: [],
      mentions: [],
      links: [
        {
          link: 'https://www.naver.com',
          start: 12,
          end: 33,
        },
      ],
      medias: [],
    });
  });

  it('should extract complex case properly in text', () => {
    expect(
      forEditPaint({
        text: 'hello world https://www.naver.com should @sangmin return #opt zozo',
        medias: [convertToMedia('https://www.naver.com', 'image')],
      }),
    ).toStrictEqual({
      text: 'hello world https://www.naver.com should @sangmin return #opt zozo',
      taggedUserIds: [],
      quotePaintId: '',
      inReplyToPaintId: '',
      hashtags: [{ tag: 'opt', start: 57, end: 60 }],
      mentions: [{ mention: 'sangmin', start: 41, end: 48, userId: '' }],
      links: [
        {
          link: 'https://www.naver.com',
          start: 12,
          end: 33,
        },
      ],
      medias: [{ type: 'image', path: 'https://www.naver.com' }],
    });
  });

  it('should index shift in with long-link', () => {
    expect(
      forEditPaint({
        text: 'hello world https://www.naver.com/12345678901 should @sangmin1 return #opt zozo https://www.naver.com/98765432101 @sangmin2 #opt',
        medias: [convertToMedia('https://www.naver.com', 'image')],
      }),
    ).toStrictEqual({
      text: 'hello world https://www.naver.com/12345678901 should @sangmin1 return #opt zozo https://www.naver.com/98765432101 @sangmin2 #opt',
      taggedUserIds: [],
      quotePaintId: '',
      inReplyToPaintId: '',
      hashtags: [
        { tag: 'opt', start: 60, end: 63 },
        { tag: 'opt', start: 104, end: 107 },
      ],
      mentions: [
        { mention: 'sangmin1', start: 43, end: 51, userId: '' },
        { mention: 'sangmin2', start: 94, end: 102, userId: '' },
      ],
      links: [
        {
          link: 'https://www.naver.com/12345678901',
          start: 12,
          end: 35,
        },
        {
          link: 'https://www.naver.com/98765432101',
          start: 70,
          end: 93,
        },
      ],
      medias: [{ type: 'image', path: 'https://www.naver.com' }],
    });
  });
});
