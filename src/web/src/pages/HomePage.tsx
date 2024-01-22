import { useState } from 'react';
import { toast } from 'react-toastify';
import { useNavigate } from '@tanstack/react-router';

import type { TimelineItem } from '@/@types';
import { createDummyTimelineItem } from '@/utils';
import { Tabs, Header, ContentLayout, TimelineItemBox } from '@/components';
import {
  ReplyBottomSheet,
  ShareBottomSheet,
  ViewsBottomSheet,
} from '@/components/bottomSheet';
import { useThrottle } from '@/hooks';

interface BottomSheetState {
  reply: boolean;
  views: boolean;
  share: boolean;
}

const INITIAL_BOTTOM_SHEET_OPEN: BottomSheetState = {
  reply: false,
  views: false,
  share: false,
};

const INITIAL_SHOW_MORE_MENU = {
  id: '',
  show: false,
} as const;

function HomePage() {
  const navigate = useNavigate();

  const [isBottomSheetOpen, setIsBottomSheetOpen] = useState<BottomSheetState>(
    INITIAL_BOTTOM_SHEET_OPEN,
  );
  const [paints] = useState<TimelineItem[]>(() => createDummyTimelineItem(10));
  const [selectedPostId, setSelectedPostId] = useState<TimelineItem['id']>('');
  const [isShowMoreMenu, setIsShowMoreMenu] = useState<{
    id: string;
    show: boolean;
  }>(INITIAL_SHOW_MORE_MENU);

  const handleClickTimelineActionIcon = (
    id: string,
    type: keyof BottomSheetState,
  ) => {
    setSelectedPostId(id);
    setIsBottomSheetOpen((prev) => ({ ...prev, [type]: !prev[type] }));
  };

  const handleScrollLayout = useThrottle(() => {
    setIsShowMoreMenu({ id: '', show: false });
  }, 500);

  return (
    <>
      <Header
        left={{
          type: 'circlePerson',
          label: '메뉴 열기',
        }}
        center={{
          type: 'palette',
          label: '로고',
          width: 26,
        }}
        right={{
          type: 'setting',
          label: '로고',
        }}
      />
      <Tabs
        tabs={[
          {
            label: '추천',
            content: (
              <ContentLayout
                className="flex flex-col gap-[12px] pl-[12px] pr-[4px] mt-0 pb-[50px] max-h-[calc(100%-94px)] divide-y divide-blueGrey-400"
                onScroll={handleScrollLayout}
              >
                {paints.map((paint) => (
                  <TimelineItemBox
                    key={paint.id}
                    item={paint}
                    className="pt-[10px]"
                    isShowMenu={
                      isShowMoreMenu.id === paint.id && isShowMoreMenu.show
                    }
                    onClickReply={() =>
                      navigate({
                        to: '/post/edit',
                        search: { postId: paint.id },
                      })
                    }
                    onClickRetweet={() =>
                      handleClickTimelineActionIcon(paint.id, 'reply')
                    }
                    onClickHeart={() => toast('아직 지원되지 않는 기능입니다.')}
                    onClickViews={() =>
                      handleClickTimelineActionIcon(paint.id, 'views')
                    }
                    onClickShare={() =>
                      handleClickTimelineActionIcon(paint.id, 'share')
                    }
                    onClickMore={() =>
                      setIsShowMoreMenu((prev) => ({
                        id: prev.id ? '' : paint.id,
                        show: prev.id !== paint.id,
                      }))
                    }
                  />
                ))}
              </ContentLayout>
            ),
          },
          {
            label: '팔로우 중',
            content: (
              <ContentLayout className="flex flex-col gap-[12px] pl-[12px] pr-[4px] mt-0 pb-[50px] max-h-[calc(100%-94px)] divide-y divide-blueGrey-400">
                {[...paints]
                  .sort((a, b) => Number(b.id) - Number(a.id))
                  .map((paint) => (
                    <TimelineItemBox
                      key={paint.id}
                      item={paint}
                      className="pt-[10px]"
                      isShowMenu={
                        isShowMoreMenu.id === paint.id && isShowMoreMenu.show
                      }
                      onClickReply={() =>
                        navigate({
                          to: '/post/edit',
                          search: { postId: paint.id },
                        })
                      }
                      onClickRetweet={() =>
                        handleClickTimelineActionIcon(paint.id, 'reply')
                      }
                      onClickHeart={() =>
                        toast('아직 지원되지 않는 기능입니다.')
                      }
                      onClickViews={() =>
                        handleClickTimelineActionIcon(paint.id, 'views')
                      }
                      onClickShare={() =>
                        handleClickTimelineActionIcon(paint.id, 'share')
                      }
                      onClickMore={() =>
                        setIsShowMoreMenu((prev) => ({
                          id: prev.id ? '' : paint.id,
                          show: prev.id !== paint.id,
                        }))
                      }
                    />
                  ))}
              </ContentLayout>
            ),
          },
        ]}
        className="mt-[44px]"
      />
      <ReplyBottomSheet
        id={selectedPostId}
        isOpen={isBottomSheetOpen.reply}
        onClose={() =>
          setIsBottomSheetOpen((prev) => ({ ...prev, reply: false }))
        }
      />
      <ViewsBottomSheet
        isOpen={isBottomSheetOpen.views}
        onClose={() =>
          setIsBottomSheetOpen((prev) => ({ ...prev, views: false }))
        }
      />
      <ShareBottomSheet
        id={selectedPostId}
        isOpen={isBottomSheetOpen.share}
        onClose={() =>
          setIsBottomSheetOpen((prev) => ({ ...prev, share: false }))
        }
      />
    </>
  );
}

export default HomePage;
