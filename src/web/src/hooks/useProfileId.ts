import { useEffect } from 'react';
import { useQuery } from '@tanstack/react-query';

import { apis } from '@/api';
import { userIdStorage } from '@/api/AuthTokenStorage';

export const useProfileId = (): string => {
  const userIdFromStorage = userIdStorage.get();

  const { data: me } = useQuery({
    queryKey: ['user-profile', 'me'],
    queryFn: () => apis.users.getMyProfile(),
    enabled: !userIdFromStorage,
  });

  useEffect(() => {
    if (me?.id) {
      userIdStorage.set(me.id);
    }
  }, [me?.id]);

  return me?.id ?? userIdFromStorage ?? '';
};
