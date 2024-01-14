import { Example } from '@/components';
import { ContentLayout, Header } from '@/components/common';

function ChatPage() {
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
        <h1 className="text-headline-1">CHAT</h1>
        <Example />
      </ContentLayout>
    </>
  );
}

export default ChatPage;
