import type { UIEvent } from 'react';
import { motion } from 'framer-motion';
import { useRef, useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { Helmet, HelmetProvider } from 'react-helmet-async';

import { apis } from '@/api';
import type { TimelineItem } from '@/@types';
import { createDummyTimelineItem, forCloudinaryImage } from '@/utils';
import {
  Button,
  ProfileHeader,
  ProfileInformationBox,
  ProfileTabs,
  Typography,
} from '@/components';
import { useThrottle } from '@/hooks';
import { DEFAULT_BACKGROUND_IMAGE } from '@/constants';

const MIN_IMAGE_HEIGHT = 50;
const DEFAULT_IMAGE_HEIGHT = 124;
function MyProfilePage() {
  const { data: user } = useQuery({
    queryKey: ['user-profile', 'me'],
    queryFn: () => apis.users.getMyProfile(),
  });

  const scrollRef = useRef<HTMLDivElement | null>(null);
  const [paints] = useState<TimelineItem[]>(() => createDummyTimelineItem(10));
  const [imageHeight, setImageHeight] = useState<number>(DEFAULT_IMAGE_HEIGHT);
  const isExpandImage = imageHeight === DEFAULT_IMAGE_HEIGHT;

  const handleScroll = useThrottle((e: UIEvent<HTMLElement>) => {
    const padding = 220;
    const { scrollTop } = e.target as HTMLElement;

    if (scrollTop > padding && imageHeight !== MIN_IMAGE_HEIGHT) {
      setImageHeight(MIN_IMAGE_HEIGHT);
    }
    if (scrollTop < padding && imageHeight !== DEFAULT_IMAGE_HEIGHT) {
      setImageHeight(DEFAULT_IMAGE_HEIGHT);
    }
  }, 200);

  return (
    <>
      <HelmetProvider>
        <Helmet>
          <title>Easel | 프로필</title>
          <meta name="description" content="프로필 페이지" />
        </Helmet>
      </HelmetProvider>
      <main className="relative">
        <ProfileHeader isExpandImage={isExpandImage} />
        <img
          src={forCloudinaryImage(user?.backgroundImagePath, {
            resize: false,
            defaultImage: DEFAULT_BACKGROUND_IMAGE,
          })}
          alt="user-background"
          className="w-full top-0 transition-all"
          style={{
            height: imageHeight,
          }}
        />
        {isExpandImage ? (
          <div className="flex justify-end relative items-center mt-[10px] pl-3 pr-2">
            <img
              src={forCloudinaryImage(user?.profileImagePath)}
              alt="user"
              className="w-[70px] h-[70px] rounded-full border-2 border-white absolute left-3 top-[-35px]"
            />

            <Button
              variant="outlined"
              className="w-fit px-[14px] py-0 h-[30px]"
            >
              <Typography size="headline-8" color="grey-600">
                프로필 수정
              </Typography>
            </Button>
          </div>
        ) : (
          <motion.div
            className="flex flex-col gap-[-2px] absolute left-14 top-[8px]"
            initial={{ opacity: 0 }}
            animate={{ opacity: 100 }}
          >
            <Typography size="headline-7" color="white">
              {user?.nickname}
            </Typography>
            <Typography
              size="body-3"
              color="white"
              className="absolute top-[18px] whitespace-nowrap"
            >
              게시물 {paints.length}개
            </Typography>
          </motion.div>
        )}
        <div
          className="max-h-screen h-screen overflow-y-scroll overflow-x-hidden"
          ref={scrollRef}
          onScroll={handleScroll}
        >
          <ProfileInformationBox user={user} className="pl-3 pr-2" />
          <ProfileTabs className="mt-[20px]" />
        </div>
      </main>
    </>
  );
}

export default MyProfilePage;
