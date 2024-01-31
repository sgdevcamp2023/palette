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

export const forCloudinaryImage = (
  id: string,
  options:
    | {
        resize: true;
        width: ImageSize['width'];
        height: ImageSize['height'];
      }
    | { resize: false } = {
    resize: true,
    width: 400,
    height: 400,
  },
): string => {
  const image = cld.image(id);
  if (!image) {
    throw new ImageNotFoundError();
  }
  image.quality('auto');
  if (options.resize) {
    image.resize(Resize.scale().width(options.width).height(options.height));
  }
  return image.toURL();
};
