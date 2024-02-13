import { toast } from 'react-toastify';
import { Helmet, HelmetProvider } from 'react-helmet-async';

import {
  Tabs,
  Header,
  ContentLayout,
  TimelineItemList,
  AsyncBoundary,
  ErrorWithResetBox,
} from '@/components';
import { TimelineItemListSkeleton } from '@/components/skeleton';

function HomePage() {
  const handleNotSupport = () => {
    toast('아직 지원되지 않는 기능입니다.');
  };

  return (
    <>
      <HelmetProvider>
        <Helmet>
          <title>Easel | 메인</title>
          <meta name="description" content="메인 페이지" />
        </Helmet>
      </HelmetProvider>
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
          onClick: handleNotSupport,
        }}
      />
      <Tabs
        tabs={[
          {
            label: '추천',
            content: (
              <ContentLayout className="mt-0 pl-[12px] pr-[4px] pb-[50px] h-full max-h-[calc(100%-144px)]">
                <AsyncBoundary
                  pendingFallback={<TimelineItemListSkeleton />}
                  rejectedFallback={(props) => <ErrorWithResetBox {...props} />}
                >
                  <TimelineItemList type="recommend" />
                </AsyncBoundary>
              </ContentLayout>
            ),
          },
          {
            label: '팔로우 중',
            content: (
              <ContentLayout className="mt-0 pl-[12px] pr-[4px] pb-[50px] h-full max-h-[calc(100%-144px)]">
                <AsyncBoundary
                  pendingFallback={<TimelineItemListSkeleton />}
                  rejectedFallback={(props) => <ErrorWithResetBox {...props} />}
                >
                  <TimelineItemList type="follow" />
                </AsyncBoundary>
              </ContentLayout>
            ),
          },
        ]}
        className="mt-[44px]"
      />
    </>
  );
}

export default HomePage;
