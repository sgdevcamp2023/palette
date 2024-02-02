import { Cloudinary } from '@cloudinary/url-gen';
import { Resize } from '@cloudinary/url-gen/actions';

import { env } from '@/constants';
import type { ImageSize } from '@/@types';

export const cld = new Cloudinary({
  cloud: {
    cloudName: env.VITE_CLOUD_NAME,
  },
  url: {
    secure: true,
  },
});

class ImageNotFoundError extends Error {
  constructor() {
    super('이미지 경로가 잘못 되었습니다.');
  }
}

type ImageQuality =
  | 'auto'
  | 'auto:best'
  | 'auto:eco'
  | 'auto:good'
  | 'auto:low'
  | 'jpegmini'
  | 'jpegmini:best'
  | 'jpegmini:high'
  | 'jpegmini:medium';
export const forCloudinaryImage = (
  id: string,
  options:
    | {
        resize: true;
        quality?: ImageQuality;
        ratio?: '16:9' | '3:4' | '1:1' | false;
        width: ImageSize['width'];
        height: ImageSize['height'];
      }
    | { resize: false; quality?: ImageQuality } = {
    resize: true,
    quality: 'auto',
    ratio: '1:1',
    width: 400,
    height: 400,
  },
): string => {
  const image = cld.image(id);
  if (!image) {
    throw new ImageNotFoundError();
  }

  if (options.quality) {
    image.quality(options.quality);
  }

  if (options.resize && options.ratio) {
    image.resize(
      Resize.scale().width(options.width).aspectRatio(options.ratio),
    );
  } else if (options.resize && !options.ratio) {
    image.resize(Resize.scale().width(options.width).height(options.height));
  }
  return image.toURL();
};
