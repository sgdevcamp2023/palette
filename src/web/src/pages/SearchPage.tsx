import { Helmet } from 'react-helmet';

import { ContentLayout, Header } from '@/components';

function SearchPage() {
  return (
    <>
      <Helmet>
        <title>Easel | 검색</title>
        <meta name="description" content="검색 페이지" />
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
        <h1 className="text-headline-1">SEARCH</h1>
      </ContentLayout>
    </>
  );
}

export default SearchPage;
