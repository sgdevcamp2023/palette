import { Helmet, HelmetProvider } from 'react-helmet-async';

import { ContentLayout, Header } from '@/components';

function ChatPage() {
  return (
    <>
      <HelmetProvider>
        <Helmet>
          <title>Easel | 채팅</title>
          <meta name="description" content="채팅 페이지" />
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
          label: '로고',
        }}
      />
      <ContentLayout>
        <h1 className="text-headline-1">CHAT</h1>
      </ContentLayout>
    </>
  );
}

export default ChatPage;
