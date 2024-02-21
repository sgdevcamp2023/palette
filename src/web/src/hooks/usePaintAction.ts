import { useCallback, useState } from 'react';
import { useNavigate } from '@tanstack/react-router';
import { useMutation } from '@tanstack/react-query';

import { apis } from '@/api';
import { useThrottle } from './useThrottle';
import type { TimelineItem } from '@/@types';
import { usePreservedCallback } from './usePreservedCallback';

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

export const usePaintAction = ({
  userId,
  onLikeOrDislike,
}: {
  userId: string;
  onLikeOrDislike?: (id: TimelineItem['id']) => void;
}) => {
  const navigate = useNavigate();
  const [isBottomSheetOpen, setIsBottomSheetOpen] = useState<BottomSheetState>(
    INITIAL_BOTTOM_SHEET_OPEN,
  );
  const [selectedPostId, setSelectedPostId] = useState<TimelineItem['id']>('');
  const [isShowMoreMenu, setIsShowMoreMenu] = useState<{
    id: string;
    show: boolean;
  }>(INITIAL_SHOW_MORE_MENU);

  const likePaintMutate = useMutation({
    mutationKey: ['like-paint', selectedPostId],
    mutationFn: ({ paintId }: { paintId: TimelineItem['id'] }) =>
      apis.users.likePaint({ userId, paintId }),
    onMutate: (mutate) => {
      onLikeOrDislike?.(mutate.paintId);
    },
  });

  const disLikePaintMutate = useMutation({
    mutationKey: ['dislike-paint', selectedPostId],
    mutationFn: ({ paintId }: { paintId: TimelineItem['id'] }) =>
      apis.users.disLikePaint({ userId, paintId }),
    onMutate: (mutate) => {
      onLikeOrDislike?.(mutate.paintId);
    },
  });

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

  const handleClickRetweet = usePreservedCallback((id: TimelineItem['id']) => {
    handleClickTimelineActionIcon(id, 'reply');
  });

  const handleClickViews = usePreservedCallback((id: TimelineItem['id']) => {
    handleClickTimelineActionIcon(id, 'views');
  });

  const handleClickShare = usePreservedCallback((id: TimelineItem['id']) => {
    handleClickTimelineActionIcon(id, 'share');
  });

  const handleClickReply = usePreservedCallback((id: TimelineItem['id']) => {
    navigate({
      to: '/post/edit',
      search: { inReplyToPaintId: id },
    });
  });

  const handleClickHeart = (id: TimelineItem['id'], isAlreadyLike: boolean) => {
    if (isAlreadyLike) {
      disLikePaintMutate.mutate({ paintId: id });
    } else {
      likePaintMutate.mutate({ paintId: id });
    }
  };

  const handleClickMore = useCallback((id: TimelineItem['id']) => {
    setIsShowMoreMenu((prev) => ({
      id: prev.id ? '' : id,
      show: prev.id !== id,
    }));
  }, []);

  const handleCloseMenu = useCallback(() => {
    setIsShowMoreMenu({ id: '', show: false });
  }, []);

  const handleClickCloseBottomSheet = useCallback(
    (type: keyof BottomSheetState) => {
      setIsBottomSheetOpen((prev) => ({ ...prev, [type]: false }));
    },
    [],
  );

  return {
    selectedPostId,
    isShowMoreMenu,
    isBottomSheetOpen,
    onScrollLayout: handleScrollLayout,
    onClickRetweet: handleClickRetweet,
    onClickReply: handleClickReply,
    onClickHeart: handleClickHeart,
    onClickShare: handleClickShare,
    onClickViews: handleClickViews,
    onClickMore: handleClickMore,
    onCloseMenu: handleCloseMenu,
    onCloseBottomSheet: handleClickCloseBottomSheet,
  };
};

export type PaintAction = ReturnType<typeof usePaintAction>;
