import type { UIEvent } from 'react';
import { motion } from 'framer-motion';
import { useEffect, useRef, useState } from 'react';
import { useNavigate, useRouter } from '@tanstack/react-router';

import type { TimelineItem } from '@/@types';
import { DUMMY_USER, createDummyTimelineItem } from '@/utils';
import {
  AccessibleIconButton,
  Button,
  ContentLayout,
  Icon,
  Tabs,
  TimelineItemBox,
  Typography,
} from '@/components';
import { usePaintAction, useThrottle } from '@/hooks';
import {
  ReplyBottomSheet,
  ShareBottomSheet,
  ViewsBottomSheet,
} from '@/components/bottomSheet';

function TabTimlineList({
  paints,
  paintAction,
}: {
  paints: TimelineItem[];
  paintAction: ReturnType<typeof usePaintAction>;
}) {
  return (
    <>
      {paints.map((paint) => (
        <TimelineItemBox
          key={paint.id}
          item={paint}
          className="pt-[10px]"
          isShowMenu={
            paintAction.isShowMoreMenu.id === paint.id &&
            paintAction.isShowMoreMenu.show
          }
          onClickHeart={() => paintAction.onClickHeart(paint.id)}
          onClickMore={() => paintAction.onClickMore(paint.id)}
          onClickReply={() => paintAction.onClickReply(paint.id)}
          onClickShare={() => paintAction.onClickShare(paint.id)}
          onClickRetweet={() => paintAction.onClickRetweet(paint.id)}
          onClickViews={() => paintAction.onClickViews(paint.id)}
        />
      ))}
    </>
  );
}

const MIN_IMAGE_HEIGHT = 50;
const DEFAULT_IMAGE_HEIGHT = 124;
const user = DUMMY_USER;
function ProfilePage() {
  const navigate = useNavigate();
  const router = useRouter();
  const paintAction = usePaintAction();
  const scrollRef = useRef<HTMLDivElement | null>(null);
  const [paints] = useState<TimelineItem[]>(() => createDummyTimelineItem(10));
  const [imageHeight, setImageHeight] = useState<number>(DEFAULT_IMAGE_HEIGHT);
  const isExpandImage = imageHeight === DEFAULT_IMAGE_HEIGHT;

  const handleScroll = useThrottle((e: UIEvent<HTMLElement>) => {
    const padding = 200;
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
        className="max-h-screen h-screen overflow-y-scroll"
        ref={scrollRef}
        onScroll={handleScroll}
      >
        <div className="pl-3 pr-2">
          <div className="max-h-screen overflow-y-scroll">
            <Typography
              size="headline-4"
              color="grey-600"
              className="mt-[10px]"
            >
              {user.nickname}
            </Typography>
            <Typography size="body-1" color="blueGrey-800" className="mt-[6px]">
              {user.username}
            </Typography>

            <Typography
              size="body-2"
              color="blueGrey-800"
              className="mt-[16px]"
            >
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
        </div>
        <Tabs
          className="mt-[20px]"
          menuClassName="sticky top-0"
          tabs={[
            {
              label: '게시물',
              content: (
                <ContentLayout className="flex flex-col divide-y divide-blueGrey-400 pl-[12px] pr-[4px] gap-[12px] mt-0 max-h-none">
                  <TabTimlineList paintAction={paintAction} paints={paints} />
                </ContentLayout>
              ),
            },
            {
              label: '답글',
              content: (
                <TabTimlineList
                  paintAction={paintAction}
                  paints={[...paints].reverse()}
                />
              ),
            },
            {
              label: '하이라이트',
              content: (
                <TabTimlineList paintAction={paintAction} paints={paints} />
              ),
            },
            {
              label: '미디어',
              content: (
                <TabTimlineList
                  paintAction={paintAction}
                  paints={[...paints].filter(
                    (paint) => paint.includes.medias.length,
                  )}
                />
              ),
            },
            {
              label: '마음에 들어요',
              content: (
                <TabTimlineList
                  paintAction={paintAction}
                  paints={[...paints].reverse()}
                />
              ),
            },
          ]}
        />
      </div>

      <ReplyBottomSheet
        id={paintAction.selectedPostId}
        isOpen={paintAction.isBottomSheetOpen.reply}
        onClose={() => paintAction.onCloseBottomSheet('reply')}
      />
      <ViewsBottomSheet
        isOpen={paintAction.isBottomSheetOpen.views}
        onClose={() => paintAction.onCloseBottomSheet('views')}
      />
      <ShareBottomSheet
        id={paintAction.selectedPostId}
        isOpen={paintAction.isBottomSheetOpen.share}
        onClose={() => paintAction.onCloseBottomSheet('share')}
      />
    </main>
  );
}

export default ProfilePage;
