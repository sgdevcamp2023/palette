import { useState } from 'react';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { Example, FramerExample } from './components';

function App() {
  const [queryClient] = useState(() => new QueryClient());

  return (
    <QueryClientProvider client={queryClient}>
      <div>
        <h1 className="text-4xl text-orange-500">PALETTE</h1>
        <Example />
        <FramerExample />
      </div>
    </QueryClientProvider>
  );
}

export default App;
