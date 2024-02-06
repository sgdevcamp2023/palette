import { useState } from 'react';
import { useNavigate } from '@tanstack/react-router';
import { Helmet, HelmetProvider } from 'react-helmet-async';

import { searchResultRoute } from '@/routes';
import {
  AsyncBoundary,
  ContentLayout,
  ErrorWithResetBox,
  Header,
  Tabs,
  TimelineItemList,
  UserList,
} from '@/components';
import {
  TimelineItemListSkeleton,
  UserListSkeleton,
} from '@/components/skeleton';

function SearchResultPage() {
  const navigate = useNavigate();
  const search = searchResultRoute.useSearch();
  const [keyword, setKeyword] = useState<string>(() => search.keyword ?? '');
  console.log(keyword);

  return (
    <>
      <HelmetProvider>
        <Helmet>
          <title>Easel | 검색 결과</title>
          <meta name="description" content="검색 결과 페이지" />
        </Helmet>
      </HelmetProvider>
      <Header
        left={{
          type: 'circlePerson',
          label: '메뉴 열기',
        }}
        center={{
          type: 'node',
          node: (
            <input
              id="search"
              type="text"
              placeholder="검색"
              className="w-[calc(100%-72px)] ml-[36px] mr-[24px] rounded-[16px] pl-[12px] bg-grey-200 flex items-center justify-center
                placeholder:transition-all placeholder:absolute placeholder:left-1/2 placeholder:top-1/2 placeholder:-translate-x-1/2 placeholder:-translate-y-1/2 placeholder-shown:border-grey-400 placeholder:text-[14px] placeholder:text-blueGrey-800
                focus:outline-0 focus:placeholder:left-[24px]
              "
              value={keyword}
              onChange={(e) => setKeyword(e.target.value)}
              onKeyUp={(e) => {
                if (e.key === 'Enter') {
                  navigate({ to: '/search/result', search: { keyword } });
                }
              }}
            />
          ),
        }}
        right={{
          type: 'blingStar',
          label: '아이콘',
        }}
      />
      <ContentLayout className="px-0">
        <Tabs
          tabs={[
            {
              label: '추천',
              content: (
                <AsyncBoundary
                  pendingFallback={
                    <TimelineItemListSkeleton className="px-[20px]" />
                  }
                  rejectedFallback={(props) => <ErrorWithResetBox {...props} />}
                >
                  <TimelineItemList
                    type="search-recommend"
                    className="px-[20px]"
                  />
                </AsyncBoundary>
              ),
            },
            {
              label: '최근',
              content: (
                <AsyncBoundary
                  pendingFallback={
                    <TimelineItemListSkeleton className="px-[20px]" />
                  }
                  rejectedFallback={(props) => <ErrorWithResetBox {...props} />}
                >
                  <TimelineItemList
                    type="search-recent"
                    className="px-[20px]"
                  />
                </AsyncBoundary>
              ),
            },
            {
              label: '사용자',
              content: (
                <AsyncBoundary
                  pendingFallback={
                    <UserListSkeleton className="px-[20px] mt-[10px]" />
                  }
                  rejectedFallback={(props) => <ErrorWithResetBox {...props} />}
                >
                  <UserList
                    type="search"
                    className="px-[20px] mt-[10px] pb-[64px]"
                  />
                </AsyncBoundary>
              ),
            },
            {
              label: '미디어',
              content: (
                <AsyncBoundary
                  pendingFallback={
                    <TimelineItemListSkeleton className="px-[20px]" />
                  }
                  rejectedFallback={(props) => <ErrorWithResetBox {...props} />}
                >
                  <TimelineItemList type="search-media" className="px-[20px]" />
                </AsyncBoundary>
              ),
            },
          ]}
        />
      </ContentLayout>
    </>
  );
}

export default SearchResultPage;
