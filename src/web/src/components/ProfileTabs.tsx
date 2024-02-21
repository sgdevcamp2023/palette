import { toast } from 'react-toastify';

import AsyncBoundary from './AsyncBoundary';
import ContentLayout from './ContentLayout';
import TimelineItemList from './TimelineItemList';
import ErrorWithResetBox from './ErrorWithResetBox';
import { Button, Tabs, Typography } from './common';
import { TimelineItemListSkeleton } from './skeleton';

interface ProfileTabsProps {
  className?: string;
}

function ProfileTabs({ className }: ProfileTabsProps) {
  return (
    <Tabs
      className={className}
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
                  <div className="py-10 pb-32">
                    <ErrorWithResetBox {...props} />
                  </div>
                )}
              >
                <TimelineItemList type="post" className="pb-32" />
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
                  <div className="py-10 pb-32">
                    <ErrorWithResetBox {...props} />
                  </div>
                )}
              >
                <TimelineItemList type="reply" className="pb-32" />
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
              <div className="pt-10 px-6">
                <Typography size="headline-2" color="grey-600">
                  프로필에 하이라이트 추가
                </Typography>
                <Typography
                  size="body-2"
                  color="blueGrey-800"
                  className="mt-[14px]"
                >
                  프로필에 게시물을 하이라이트 하려면 Premium을 구독해야 합니다.
                </Typography>

                <Button
                  className="w-[190px] mt-[28px] text-[16px]"
                  onClick={() => toast('아직 지원되지 않는 기능입니다.')}
                >
                  Premium 구독하기
                </Button>
              </div>
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
                  <div className="py-10 pb-32">
                    <ErrorWithResetBox {...props} />
                  </div>
                )}
              >
                <TimelineItemList type="media" className="pb-32" />
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
                  <div className="py-10 pb-32">
                    <ErrorWithResetBox {...props} />
                  </div>
                )}
              >
                <TimelineItemList type="heart" className="pb-32" />
              </AsyncBoundary>
            </ContentLayout>
          ),
        },
      ]}
    />
  );
}

export default ProfileTabs;
