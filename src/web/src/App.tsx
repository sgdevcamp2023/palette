import { useState } from 'react';
import { ToastContainer } from 'react-toastify';
import { RouterProvider } from '@tanstack/react-router';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';

import 'react-toastify/dist/ReactToastify.css';

import { router } from './routes';

function App() {
  const [queryClient] = useState(() => new QueryClient());

  return (
    <QueryClientProvider client={queryClient}>
      <RouterProvider router={router} />
      <ToastContainer position="top-center" autoClose={3000} theme="dark" />
    </QueryClientProvider>
  );
}

export default App;
