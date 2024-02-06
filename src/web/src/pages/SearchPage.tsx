import { useState } from 'react';
import { useNavigate } from '@tanstack/react-router';
import { Helmet, HelmetProvider } from 'react-helmet-async';

import { Button, ContentLayout, Header, Typography } from '@/components';

function SearchPage() {
  const navigate = useNavigate();
  const [keyword, setKeyword] = useState<string>('');

  return (
    <>
      <HelmetProvider>
        <Helmet>
          <title>Easel | 검색</title>
          <meta name="description" content="검색 페이지" />
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
                  navigate({
                    to: '/search/result',
                    search: { keyword },
                  });
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
      <ContentLayout className="px-0 border-t-[1px] border-t-blueGrey-200">
        <Typography
          size="headline-5"
          color="grey-600"
          className="py-[12px] px-[20px]"
        >
          사용자를 위한 추천
        </Typography>
        <span className="block w-full h-[1px] bg-blueGrey-200 content-['']" />
        <Typography
          size="headline-3"
          color="grey-600"
          className="pt-[36px] px-[20px] whitespace-pre"
        >
          {`새로운 트렌드가\n준비되지 않았습니다!`}
        </Typography>
        <Typography
          size="body-1"
          color="blueGrey-800"
          className="pt-[14px] px-[20px] whitespace-pre-line"
        >
          트렌드를 계속 만들고 있어요. 추후 버전에서의 업데이트될 예정입니다.
        </Typography>
        <Button className="w-[180px] mt-[16px] ml-[20px] py-[12px]">
          <Typography size="body-2" color="white" className="">
            Premium + | 결제하러 가기
          </Typography>
        </Button>
      </ContentLayout>
    </>
  );
}

export default SearchPage;
