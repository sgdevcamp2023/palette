import type { UserProfile } from '@/@types';
import { Icon, Typography } from './common';
import { cn } from '@/utils';
import { memo } from 'react';

interface ProfileInformationBoxProps {
  className?: string;
  user?: UserProfile;
}

function ProfileInformationBox({
  className,
  user,
}: ProfileInformationBoxProps) {
  return (
    <div className={cn('', className)}>
      <Typography size="headline-4" color="grey-600" className="mt-[10px]">
        {user?.nickname}
      </Typography>
      <Typography size="body-1" color="blueGrey-800" className="mt-[6px]">
        {user?.username}
      </Typography>

      <Typography size="body-2" color="blueGrey-800" className="mt-[16px]">
        {user?.introduce}
      </Typography>

      <div className="flex gap-[4px] mt-[12px] items-center">
        <Icon type="calendar" width={12} height={12} />
        <Typography size="body-2" color="blueGrey-800">
          {(user?.joinedAt
            ? new Date(user.joinedAt)
            : new Date()
          ).toDateString()}
        </Typography>
      </div>

      <div className="flex gap-[10px] mt-[14px]">
        <div role="button" tabIndex={0} className="flex gap-[4px]">
          <Typography as="span" size="headline-8" color="grey-600">
            {user?.followingCount}
          </Typography>
          <Typography as="span" size="body-2" color="blueGrey-800">
            팔로잉
          </Typography>
        </div>
        <div className="flex gap-[4px]">
          <Typography as="span" size="headline-8" color="grey-600">
            {user?.followerCount}
          </Typography>
          <Typography as="span" size="body-2" color="blueGrey-800">
            팔로워
          </Typography>
        </div>
      </div>
    </div>
  );
}

const MemoizedProfileInformationBox = memo(ProfileInformationBox);

export default MemoizedProfileInformationBox;
