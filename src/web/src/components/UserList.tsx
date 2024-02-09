import { useNavigate } from '@tanstack/react-router';
import { useSuspenseQuery } from '@tanstack/react-query';

import type { User } from '@/@types';
import { cn, createDummyUsers, forCloudinaryImage } from '@/utils';
import { Button, LazyImage, Typography } from './common';

interface UserListProps {
  type: 'search' | 'following' | 'follower';
  className?: string;
}

function delay(ms: number): Promise<
  (Pick<
    User,
    'id' | 'username' | 'nickname' | 'profileImagePath' | 'introduce'
  > & {
    isFollowing: boolean;
  })[]
> {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve(
        createDummyUsers(30).map((user) => ({
          ...user,
          isFollowing: Math.random() < 0.04,
        })),
      );
    }, ms);
  });
}

function getQueryFnByType(type: UserListProps['type']): Promise<
  (Pick<
    User,
    'id' | 'username' | 'nickname' | 'profileImagePath' | 'introduce'
  > & {
    isFollowing: boolean;
  })[]
> {
  switch (type) {
    case 'search':
      return delay(1250);
    case 'follower':
      return delay(1250);
    case 'following':
      return delay(1250);
    default:
      return delay(1250);
  }
}

function UserList({ className, type }: UserListProps) {
  const navigate = useNavigate();

  const { data: users } = useSuspenseQuery({
    queryKey: ['user-list', type],
    queryFn: () => getQueryFnByType(type),
  });

  return (
    <section className={cn('w-full flex flex-col gap-[32px]', className)}>
      {users.map((user) => (
        <li key={user.id} className="w-full list-none">
          <div
            className="w-full flex gap-[8px]"
            role="button"
            tabIndex={0}
            onClick={() =>
              navigate({ to: '/profile/$userId', params: { userId: user.id } })
            }
          >
            <LazyImage
              src={forCloudinaryImage(user.profileImagePath, {
                resize: true,
                width: 40,
                height: 40,
              })}
              alt="user-profile"
              className="w-[40px] min-w-[40px] h-[40px] rounded-full"
            />

            <div className="w-full flex flex-col">
              <div className="w-full flex items-center justify-between">
                <div>
                  <Typography size="headline-8" color="grey-600">
                    {user.nickname}
                  </Typography>
                  <Typography size="body-1" color="blueGrey-800">
                    {user.username}
                  </Typography>
                </div>
                {user.isFollowing ? (
                  <Button variant="outlined" className="w-[90px] h-[30px]">
                    <Typography size="body-3" color="grey-600">
                      팔로잉
                    </Typography>
                  </Button>
                ) : (
                  <Button className="w-[90px] h-[30px]">
                    <Typography size="body-3" color="white">
                      팔로우
                    </Typography>
                  </Button>
                )}
              </div>
              <Typography size="body-1" color="grey-600" className="mt-[8px]">
                {user.introduce}
              </Typography>
            </div>
          </div>
        </li>
      ))}
    </section>
  );
}

export default UserList;
