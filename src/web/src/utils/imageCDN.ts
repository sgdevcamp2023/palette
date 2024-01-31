import { Cloudinary } from '@cloudinary/url-gen';
import { Resize } from '@cloudinary/url-gen/actions/resize';

import type { ImageSize } from '@/@types';

export const cld = new Cloudinary({
  cloud: {
    cloudName: import.meta.env.VITE_CLOUD_NAME,
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
  options: ImageSize = { width: 400, height: 400 },
): string => {
  const image = cld.image(id);
  if (!image) {
    throw new ImageNotFoundError();
  }
  image.resize(Resize.scale().width(options.width).height(options.height));
  return image.toURL();
};
