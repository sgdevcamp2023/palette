import { type ComponentProps, Suspense } from 'react';
import { ErrorBoundary } from 'react-error-boundary';
import { useQueryErrorResetBoundary } from '@tanstack/react-query';

import { ErrorFallbackPage } from '@/pages';

type ErrorBoundaryProps = ComponentProps<typeof ErrorBoundary>;

interface AsyncBoundaryProps
  extends Omit<ErrorBoundaryProps, 'fallbackRender'> {
  pendingFallback?: ComponentProps<typeof Suspense>['fallback'];
  rejectedFallback?: ErrorBoundaryProps['fallbackRender'];
}

function AsyncBoundary({
  pendingFallback,
  rejectedFallback,
  children,
}: React.PropsWithChildren<AsyncBoundaryProps>) {
  const { reset } = useQueryErrorResetBoundary();

  return (
    <ErrorBoundary
      fallbackRender={rejectedFallback || ErrorFallbackPage}
      onReset={reset}
    >
      <Suspense fallback={pendingFallback || <div>Loading...</div>}>
        {children}
      </Suspense>
    </ErrorBoundary>
  );
}

export default AsyncBoundary;
