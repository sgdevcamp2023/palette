import type { UIEvent } from 'react';
import { motion } from 'framer-motion';
import { useEffect, useRef, useState } from 'react';
import { useNavigate, useRouter } from '@tanstack/react-router';

import type { TimelineItem } from '@/@types';
import { DUMMY_USER, createDummyTimelineItem } from '@/utils';
import {
  AccessibleIconButton,
  AsyncBoundary,
  Button,
  ContentLayout,
  ErrorWithResetBox,
  Icon,
  Tabs,
  TimelineItemList,
  Typography,
} from '@/components';
import { useThrottle } from '@/hooks';
import { TimelineItemListSkeleton } from '@/components/skeleton';

const MIN_IMAGE_HEIGHT = 50;
const DEFAULT_IMAGE_HEIGHT = 124;
const user = DUMMY_USER;
function ProfilePage() {
  const navigate = useNavigate();
  const router = useRouter();
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

  useEffect(() => {}, []);

  return (
    <main className="relative">
      <header className="absolute top-0 flex justify-between items-center px-[14px] w-[420px] max-w-full h-[44px] z-[100]">
        <AccessibleIconButton
          iconType="leftStickArrow"
          label="뒤로가기"
          fill="white"
          width={isExpandImage ? 20 : 16}
          height={isExpandImage ? 20 : 16}
          className="absolute left-[20px] z-50"
          onClick={() => router.history.back()}
        />
        <div
          className="relative bg-black opacity-60 rounded-full w-[32px] h-[32px] flex justify-center items-center"
          style={{
            width: isExpandImage ? 32 : 28,
            height: isExpandImage ? 32 : 28,
          }}
        />
        <AccessibleIconButton
          iconType="search"
          label="검색 페이지"
          fill="white"
          width={isExpandImage ? 16 : 12}
          height={isExpandImage ? 16 : 12}
          className="absolute right-[22px] z-50"
          onClick={() => navigate({ to: '/search' })}
        />
        <div
          className="relative bg-black opacity-60 rounded-full w-[32px] h-[32px] flex justify-center items-center"
          style={{
            width: isExpandImage ? 32 : 28,
            height: isExpandImage ? 32 : 28,
          }}
        />
      </header>
      <img
        src={user.backgroundImagePath}
        alt="user-background"
        className="w-full top-0 transition-all"
        style={{
          height: imageHeight,
        }}
      />
      {isExpandImage ? (
        <div className="flex justify-end relative items-center mt-[10px] pl-3 pr-2">
          <img
            src={user.profileImagePath}
            alt="user"
            className="w-[70px] h-[70px] rounded-full border-2 border-white absolute left-3 top-[-35px]"
          />

          <Button variant="outlined" className="w-fit px-[14px] py-0 h-[30px]">
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
            {user.nickname}
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
        <div className="pl-3 pr-2">
          <Typography size="headline-4" color="grey-600" className="mt-[10px]">
            {user.nickname}
          </Typography>
          <Typography size="body-1" color="blueGrey-800" className="mt-[6px]">
            {user.username}
          </Typography>

          <Typography size="body-2" color="blueGrey-800" className="mt-[16px]">
            {user.introduce}
          </Typography>

          <div className="flex gap-[4px] mt-[12px] items-center">
            <Icon type="calendar" width={12} height={12} />
            <Typography size="body-2" color="blueGrey-800">
              {user.createdAt.toDateString()}
            </Typography>
          </div>

          <div className="flex gap-[10px] mt-[14px]">
            <div role="button" tabIndex={0} className="flex gap-[4px]">
              <Typography as="span" size="headline-8" color="grey-600">
                {user.followings}
              </Typography>
              <Typography as="span" size="body-2" color="blueGrey-800">
                팔로잉
              </Typography>
            </div>
            <div className="flex gap-[4px]">
              <Typography as="span" size="headline-8" color="grey-600">
                {user.followers}
              </Typography>
              <Typography as="span" size="body-2" color="blueGrey-800">
                팔로워
              </Typography>
            </div>
          </div>
        </div>
        <Tabs
          className="mt-[20px]"
          menuClassName="sticky top-0"
          tabs={[
            {
              label: '게시물',
              content: (
                <ContentLayout
                  as="section"
                  className="mt-0 pl-[12px] pr-[4px] max-h-none"
                >
                  <AsyncBoundary
                    pendingFallback={<TimelineItemListSkeleton />}
                    rejectedFallback={(props) => (
                      <ErrorWithResetBox {...props} />
                    )}
                  >
                    <TimelineItemList type="my-post" />
                  </AsyncBoundary>
                </ContentLayout>
              ),
            },
            {
              label: '답글',
              content: (
                <ContentLayout
                  as="section"
                  className="mt-0 pl-[12px] pr-[4px] max-h-none"
                >
                  <AsyncBoundary
                    pendingFallback={<TimelineItemListSkeleton />}
                    rejectedFallback={(props) => (
                      <ErrorWithResetBox {...props} />
                    )}
                  >
                    <TimelineItemList type="my-reply" />
                  </AsyncBoundary>
                </ContentLayout>
              ),
            },
            {
              label: '하이라이트',
              content: (
                <ContentLayout
                  as="section"
                  className="mt-0 pl-[12px] pr-[4px] max-h-none"
                >
                  <AsyncBoundary
                    pendingFallback={<TimelineItemListSkeleton />}
                    rejectedFallback={(props) => (
                      <ErrorWithResetBox {...props} />
                    )}
                  >
                    <TimelineItemList type="my-post" />
                  </AsyncBoundary>
                </ContentLayout>
              ),
            },
            {
              label: '미디어',
              content: (
                <ContentLayout
                  as="section"
                  className="mt-0 pl-[12px] pr-[4px] max-h-none"
                >
                  <AsyncBoundary
                    pendingFallback={<TimelineItemListSkeleton />}
                    rejectedFallback={(props) => (
                      <ErrorWithResetBox {...props} />
                    )}
                  >
                    <TimelineItemList type="media" />
                  </AsyncBoundary>
                </ContentLayout>
              ),
            },
            {
              label: '마음에 들어요',
              content: (
                <ContentLayout
                  as="section"
                  className="mt-0 pl-[12px] pr-[4px] max-h-none"
                >
                  <AsyncBoundary
                    pendingFallback={<TimelineItemListSkeleton />}
                    rejectedFallback={(props) => (
                      <ErrorWithResetBox {...props} />
                    )}
                  >
                    <TimelineItemList type="heart" />
                  </AsyncBoundary>
                </ContentLayout>
              ),
            },
          ]}
        />
      </div>
    </main>
  );
}

export default ProfilePage;
