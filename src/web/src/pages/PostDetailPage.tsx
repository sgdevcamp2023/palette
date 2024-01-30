import { useLayoutEffect, useRef } from 'react';
import { useNavigate, useRouter } from '@tanstack/react-router';

import { usePaintAction } from '@/hooks';
import { postDetailRoute } from '@/routes';
import {
  ContentLayout,
  Header,
  MainPostBox,
  TimelineItemBox,
  Typography,
} from '@/components';
import { DUMMY_USER, createDummyTimelineItem } from '@/utils';

function PostDetailPage() {
  const me = DUMMY_USER;
  const router = useRouter();
  const navigate = useNavigate();
  const paintAction = usePaintAction();
  const params = postDetailRoute.useParams();

  const parentRef = useRef<HTMLDivElement>(null);
  const mainPost = createDummyTimelineItem(10)[Number(params.postId) % 10];
  const mainPostRef = useRef<HTMLDivElement>(null);
  const beforePosts = createDummyTimelineItem(3);
  const beforePostRef = useRef<HTMLDivElement>(null);
  const afterPosts = createDummyTimelineItem(3);

  useLayoutEffect(() => {
    const $mainPost = mainPostRef.current;
    const $beforePost = beforePostRef.current;
    const $parent = parentRef.current;

    if ($mainPost && $beforePost && $parent) {
      const headerOffset = 50;
      $mainPost.scrollIntoView();
      $parent.scrollBy(0, -headerOffset);
    }
  }, [mainPost]);

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
        className="px-0 py-0 mb-0 overflow-hidden max-h-none"
        isShowFloatingButton={false}
      >
        <div
          ref={parentRef}
          className="px-[10px] overflow-y-scroll max-h-[calc(100vh-94px)]"
        >
          {beforePosts.length > 0 && (
            <div
              ref={beforePostRef}
              className="flex flex-col gap-[12px] w-full h-full mt-[44px] mb-[24px]"
            >
              {beforePosts.map((post) => (
                <TimelineItemBox
                  key={post.id}
                  item={post}
                  className="pt-[12px]"
                  isShowMenu={
                    paintAction.isShowMoreMenu.id === post.id &&
                    paintAction.isShowMoreMenu.show
                  }
                  onClickReply={() =>
                    navigate({
                      to: '/post/edit',
                      search: { postId: post.id },
                    })
                  }
                  onClickRetweet={() => paintAction.onClickRetweet(post.id)}
                  onClickHeart={() => paintAction.onClickHeart(post.id)}
                  onClickViews={() => paintAction.onClickViews(post.id)}
                  onClickShare={() => paintAction.onClickShare(post.id)}
                  onClickMore={() => paintAction.onClickMore(post.id)}
                />
              ))}
            </div>
          )}

          <MainPostBox
            item={mainPost}
            ref={mainPostRef}
            className={afterPosts.length === 0 ? 'mb-[24px]' : ''}
            isFollow={mainPost.authorId === ''}
            isShowMenu={
              paintAction.isShowMoreMenu.id === mainPost.id &&
              paintAction.isShowMoreMenu.show
            }
            onClickReply={() =>
              navigate({
                to: '/post/edit',
                search: { postId: mainPost.id },
              })
            }
            onClickRetweet={() => paintAction.onClickRetweet(mainPost.id)}
            onClickHeart={() => paintAction.onClickHeart(mainPost.id)}
            onClickViews={() => paintAction.onClickViews(mainPost.id)}
            onClickShare={() => paintAction.onClickShare(mainPost.id)}
            onClickMore={() => paintAction.onClickMore(mainPost.id)}
          />

          {afterPosts.length > 0 && (
            <div className="flex flex-col gap-[12px] w-full h-full mb-[14px] divide-y divide-blueGrey-400 border-t-[1px] border-blueGrey-200">
              {afterPosts.map((post) => (
                <TimelineItemBox
                  key={post.id}
                  item={post}
                  className="pt-[12px]"
                  isShowMenu={
                    paintAction.isShowMoreMenu.id === post.id &&
                    paintAction.isShowMoreMenu.show
                  }
                  onClickReply={() =>
                    navigate({
                      to: '/post/edit',
                      search: { postId: post.id },
                    })
                  }
                  onClickRetweet={() => paintAction.onClickRetweet(post.id)}
                  onClickHeart={() => paintAction.onClickHeart(post.id)}
                  onClickViews={() => paintAction.onClickViews(post.id)}
                  onClickShare={() => paintAction.onClickShare(post.id)}
                  onClickMore={() => paintAction.onClickMore(post.id)}
                />
              ))}
            </div>
          )}
        </div>
      </ContentLayout>

      {/* 게시글 작성 */}
      <button
        type="button"
        className="fixed bottom-[50px] px-[10px] w-full h-[50px] items-center bg-white flex gap-[6px] pt-[6px] border-t border-t-blueGrey400 text-left"
        onClick={() =>
          navigate({ to: '/post/edit', search: { postId: mainPost.id } })
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
    </>
  );
}

export default PostDetailPage;
