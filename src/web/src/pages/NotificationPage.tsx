import { Example } from '@/components';
import { ContentLayout, Header } from '@/components/common';

function NotificationPage() {
  return (
    <>
      <Header
        left={{
          type: 'circlePerson',
          label: '취소',
          onClick: () => console.log('hi'),
        }}
        center={{
          type: 'palette',
          label: '로고',

          width: 26,
        }}
        right={{
          type: 'setting',
          label: '로고',
          onClick: () => console.log('hi'),
        }}
      />
      <ContentLayout>
        <h1 className="text-headline-1">NOTIFICATION</h1>
        <Example />
      </ContentLayout>
    </>
  );
}

export default NotificationPage;