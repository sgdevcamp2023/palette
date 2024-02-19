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

  return me?.id ?? userIdFromStorage ?? '';
};
