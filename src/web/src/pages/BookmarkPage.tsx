import { toast } from 'react-toastify';
import { useNavigate } from '@tanstack/react-router';
import { Helmet, HelmetProvider } from 'react-helmet-async';

import {
  Header,
  ContentLayout,
  TimelineItemList,
  AsyncBoundary,
  ErrorWithResetBox,
} from '@/components';
import { TimelineItemListSkeleton } from '@/components/skeleton';

function BookMarkPage() {
  const navigate = useNavigate();

  const handleNotSupport = () => {
    toast('아직 지원되지 않는 기능입니다.');
  };

  return (
    <>
      <HelmetProvider>
        <Helmet>
          <title>Easel | 북마크</title>
          <meta name="description" content="북마크 페이지" />
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
          onClick: () => navigate({ to: '/home' }),
        }}
        right={{
          type: 'setting',
          label: '설정',
          onClick: handleNotSupport,
        }}
      />
      <ContentLayout className="mt-[50px] pl-[12px] pr-[4px] pb-[50px] h-full max-h-[calc(100%-144px)]">
        <AsyncBoundary
          pendingFallback={<TimelineItemListSkeleton />}
          rejectedFallback={(props) => <ErrorWithResetBox {...props} />}
        >
          <TimelineItemList type="bookmark" />
        </AsyncBoundary>
      </ContentLayout>
    </>
  );
}

export default BookMarkPage;
