import { memo, useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import type { Dispatch, SetStateAction } from 'react';

import Header from './Header';
import { apis } from '@/api';
import type { User } from '@/@types';
import { Spinner } from './skeleton';
import { Typography, Input } from './common';
import { forCloudinaryImage } from '@/utils';

interface TagSearchUserModalProps {
  tags: Pick<User, 'id' | 'nickname'>[];
  setTags: Dispatch<SetStateAction<Pick<User, 'id' | 'nickname'>[]>>;
  onClose: VoidFunction;
}

function TagSearchUserModal({
  tags,
  setTags,
  onClose,
}: TagSearchUserModalProps) {
  const [value, setValue] = useState<string>('');
  const { data: candidateList, isLoading } = useQuery({
    queryKey: ['search', value],
    queryFn: () => apis.users.searchUserByDisplayName(value),
    enabled: value !== '',
  });

  return (
    <div className="absolute top-0 z-[9999] w-full h-full bg-white max-w-[420px]">
      <Header
        center={{
          type: 'text',
          label: '사용자 태그하기',
        }}
        right={{
          type: 'text',
          label: '완료',
          onClick: onClose,
        }}
      />
      <div className="mt-[80px] px-[24px]">
        <Input
          label="태그"
          value={value}
          onChange={(e) => setValue(e.target.value)}
        />
        <div className="flex flex-col gap-[16px] justify-center mt-4">
          <div className="flex gap-2">
            {tags.map((tag) => (
              <Typography
                key={tag.id}
                size="body-3"
                className="border border-BlueGrey400 py-2 px-3 rounded-[24px]"
              >
                {tag.nickname}
              </Typography>
            ))}
          </div>
          {isLoading ? (
            <Spinner />
          ) : (
            candidateList?.map((user) => (
              <button
                type="button"
                key={user.id}
                className="flex gap-[4px] items-center px-[12px] pt-[12px]"
                onClick={() =>
                  setTags((prev) => [
                    ...prev,
                    { id: user.id, nickname: user.nickname },
                  ])
                }
              >
                <img
                  src={forCloudinaryImage(user.imagePath, {
                    resize: true,
                    width: 100,
                    height: 100,
                    ratio: false,
                    quality: 'auto:low',
                  })}
                  alt={`${user.username}`}
                  className="rounded-full w-[20px] h-[20px] min-w-[20px] max-w-[20px] aspect-video"
                />
                <Typography size="headline-8" color="grey-600">
                  {user.nickname}
                </Typography>
                <Typography size="body-1" color="blueGrey-800">
                  {user.username}
                </Typography>
              </button>
            ))
          )}
        </div>
      </div>
    </div>
  );
}

const MemoizedTagSearchUserModal = memo(TagSearchUserModal);

export default MemoizedTagSearchUserModal;
