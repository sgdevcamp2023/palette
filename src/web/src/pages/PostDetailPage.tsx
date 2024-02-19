import { useRef } from 'react';
import { useQuery } from '@tanstack/react-query';
import { Helmet, HelmetProvider } from 'react-helmet-async';
import { useNavigate, useRouter } from '@tanstack/react-router';

import { apis } from '@/api';
import { usePaintAction } from '@/hooks';
import { postDetailRoute } from '@/routes';
import {
  AfterTimelineList,
  AsyncBoundary,
  BeforeTimelineList,
  ContentLayout,
  Header,
  MainPostBox,
  Typography,
} from '@/components';
import { forCloudinaryImage } from '@/utils';
import {
  ReplyBottomSheet,
  ShareBottomSheet,
  ViewsBottomSheet,
} from '@/components/bottomSheet';
import { Spinner, TimelineItemBoxSkeleton } from '@/components/skeleton';

function PostDetailPage() {
  const { data: me } = useQuery({
    queryKey: ['user-profile', 'me'],
    queryFn: () => apis.users.getMyProfile(),
  });
  const router = useRouter();
  const navigate = useNavigate();
  const params = postDetailRoute.useParams();

  const parentRef = useRef<HTMLDivElement>(null);
  const mainPostRef = useRef<HTMLDivElement>(null);

  const paintAction = usePaintAction({ userId: me?.id ?? '' });

  return (
    <>
      <HelmetProvider>
        <Helmet>
          <title>Easel | 게시물</title>
          <meta name="description" content="게시물 페이지" />
        </Helmet>
      </HelmetProvider>
      <Header
        left={{
          type: 'leftStickArrow',
          label: '뒤로가기',
          onClick: () => router.history.back(),
        }}
        center={{
          type: 'text',
          label: '게시',
        }}
      />
      <ContentLayout
        className="px-0 py-0 mt-0 mb-0 overflow-hidden max-h-[calc(100vh-44px)]"
        isShowFloatingButton={false}
      >
        <div
          ref={parentRef}
          className="flex flex-col flex-start px-[10px] pt-[44px] pb-[50px] overflow-y-scroll max-h-[calc(100vh-44px)]"
        >
          <AsyncBoundary
            pendingFallback={<span />}
            rejectedFallback={() => <span />}
          >
            <BeforeTimelineList
              mainPostRef={mainPostRef}
              parentRef={parentRef}
              paintAction={paintAction}
            />
          </AsyncBoundary>

          <AsyncBoundary pendingFallback={<TimelineItemBoxSkeleton />}>
            <MainPostBox
              ref={mainPostRef}
              paintAction={paintAction}
              className="mt-[24px]"
            />
          </AsyncBoundary>

          <AsyncBoundary
            pendingFallback={<Spinner className="mt-10" />}
            rejectedFallback={() => <span />}
          >
            <AfterTimelineList paintAction={paintAction} />
          </AsyncBoundary>
        </div>
      </ContentLayout>

      {/* 게시글 작성 */}
      <button
        type="button"
        className="fixed bottom-[50px] px-[10px] w-full max-w-[420px] h-[50px] items-center bg-white flex gap-[6px] pt-[6px] border-t border-t-blueGrey400 text-left"
        onClick={() =>
          navigate({ to: '/post/edit', search: { postId: params.postId } })
        }
      >
        <img
          src={forCloudinaryImage(me?.profileImagePath)}
          alt="your profile"
          className="w-[36px] h-[36px] rounded-full"
        />
        <div className="bg-grey-200 rounded-[12px] w-full p-1.5">
          <Typography size="body-2" color="blueGrey-800" className="pl-1">
            다른 게시물 추가
          </Typography>
        </div>
      </button>
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
    </>
  );
}

export default PostDetailPage;
