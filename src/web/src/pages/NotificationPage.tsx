import { toast } from 'react-toastify';
import { Helmet, HelmetProvider } from 'react-helmet-async';

import { ContentLayout, Header, NotSupportBox } from '@/components';

function NotificationPage() {
  const handleNotSupport = () => {
    toast('아직 지원되지 않는 기능입니다.');
  };

  return (
    <>
      <HelmetProvider>
        <Helmet>
          <title>Easel | 알람</title>
          <meta name="description" content="알람 페이지" />
        </Helmet>
      </HelmetProvider>
      <Header
        left={{
          type: 'circlePerson',
          label: '취소',
        }}
        center={{
          type: 'palette',
          label: '로고',
          width: 26,
        }}
        right={{
          type: 'setting',
          label: '설정',
          onClick: handleNotSupport,
        }}
      />
      <ContentLayout className="h-full mt-0 mb-0">
        <NotSupportBox description="알림 기능은 아직 준비중이에요!" />
      </ContentLayout>
    </>
  );
}

export default NotificationPage;
