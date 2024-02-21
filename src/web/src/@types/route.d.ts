// router는 타입이 아님
// eslint-disable-next-line @typescript-eslint/consistent-type-imports
import { router } from '@/routes';

declare module '@tanstack/react-router' {
  interface Register {
    router: typeof router;
  }
}
