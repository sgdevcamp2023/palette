import { Example } from '@/components';
import { ContentLayout, Header } from '@/components/common';

function HomePage() {
  return (
    <>
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
        <h1 className="text-headline-1">HOME</h1>
        <Example />
      </ContentLayout>
    </>
  );
}

export default HomePage;
