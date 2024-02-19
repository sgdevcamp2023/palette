import { Tabs } from './common';
import AsyncBoundary from './AsyncBoundary';
import ContentLayout from './ContentLayout';
import ErrorWithResetBox from './ErrorWithResetBox';
import TimelineItemList from './TimelineItemList';
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
                <TimelineItemList type="post" />
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
                <TimelineItemList type="reply" />
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
                  <div className="py-10 pb-32">
                    <ErrorWithResetBox {...props} />
                  </div>
                )}
              >
                <TimelineItemList type="post" />
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
                  <div className="py-10 pb-32">
                    <ErrorWithResetBox {...props} />
                  </div>
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
                  <div className="py-10 pb-32">
                    <ErrorWithResetBox {...props} />
                  </div>
                )}
              >
                <TimelineItemList type="heart" />
              </AsyncBoundary>
            </ContentLayout>
          ),
        },
      ]}
    />
  );
}

export default ProfileTabs;
