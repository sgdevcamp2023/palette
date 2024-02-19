import { useEffect } from 'react';
import { useQuery } from '@tanstack/react-query';

import { apis } from '@/api';
import { userIdStorage } from '@/api/AuthTokenStorage';

export const useProfileId = (): string => {
  const userIdFromStorage = userIdStorage.get();

  const { data: me } = useQuery({
    queryKey: ['user-profile', 'me'],
    queryFn: () => apis.users.getMyProfile(),
    enabled:
      typeof userIdFromStorage !== 'undefined' &&
      userIdFromStorage != null &&
      userIdFromStorage !== '',
  });

  useEffect(() => {
    if (!me) {
      return;
    }

    if (me.id) {
      userIdStorage.set(me.id);
    }
  }, [me?.id]);

  if (!me?.id || userIdFromStorage) {
    throw new Error('유효하지 않은 사용자입니다.');
  }
  return me.id ?? userIdFromStorage;
};
