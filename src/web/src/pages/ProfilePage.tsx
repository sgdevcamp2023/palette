import { useParams } from '@tanstack/react-router';

import { ContentLayout, Header, Typography } from '@/components';
import { profileRoute } from '@/routes';

function ProfilePage() {
  const params = useParams({ from: profileRoute.fullPath });

  return (
    <>
      <Header
        left={{
          type: 'leftStickArrow',
          label: '뒤로가기',
        }}
        right={{
          type: 'search',
          label: '검색 페이지로 이동',
        }}
      />
      <ContentLayout>
        <h1 className="text-headline-1">PROFILE PAGE</h1>
        <Typography size="headline-1">{params.userId}</Typography>
      </ContentLayout>
    </>
  );
}

export default ProfilePage;
