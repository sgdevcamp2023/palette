import { useRef } from 'react';
import { useNavigate, useRouter } from '@tanstack/react-router';

import { usePaintAction } from '@/hooks';
import {
  AfterTimelineList,
  AsyncBoundary,
  BeforeTimelineList,
  ContentLayout,
  Header,
  MainPostBox,
  Typography,
} from '@/components';
import { DUMMY_USER } from '@/utils';
import {
  ReplyBottomSheet,
  ShareBottomSheet,
  ViewsBottomSheet,
} from '@/components/bottomSheet';
import { Spinner, TimelineItemBoxSkeleton } from '@/components/skeleton';
import { postDetailRoute } from '@/routes';

function PostDetailPage() {
  const me = DUMMY_USER;
  const router = useRouter();
  const navigate = useNavigate();
  const paintAction = usePaintAction();
  const params = postDetailRoute.useParams();

  const parentRef = useRef<HTMLDivElement>(null);
  const mainPostRef = useRef<HTMLDivElement>(null);

  return (
    <>
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
          <AsyncBoundary pendingFallback={<span />}>
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

          <AsyncBoundary pendingFallback={<Spinner className="mt-10" />}>
            <AfterTimelineList paintAction={paintAction} />
          </AsyncBoundary>
        </div>
      </ContentLayout>

      {/* 게시글 작성 */}
      <button
        type="button"
        className="fixed bottom-[50px] px-[10px] w-full h-[50px] items-center bg-white flex gap-[6px] pt-[6px] border-t border-t-blueGrey400 text-left"
        onClick={() =>
          navigate({ to: '/post/edit', search: { postId: params.postId } })
        }
      >
        <img
          src={me.profileImagePath}
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
