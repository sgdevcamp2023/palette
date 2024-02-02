import { Helmet } from 'react-helmet';

import { ContentLayout, Header } from '@/components';

function NotificationPage() {
  return (
    <>
      <Helmet>
        <title>Easel | 알람</title>
        <meta name="description" content="알람 페이지" />
      </Helmet>
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
          label: '로고',
        }}
      />
      <ContentLayout>
        <h1 className="text-headline-1">NOTIFICATION</h1>
      </ContentLayout>
    </>
  );
}

export default NotificationPage;
