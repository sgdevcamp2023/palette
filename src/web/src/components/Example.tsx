import { useQuery } from '@tanstack/react-query';

function Example() {
  const { isPending, error, data } = useQuery({
    queryKey: ['repoData'],
    queryFn: () =>
      fetch('https://api.github.com/repos/TanStack/query').then((res) =>
        res.json(),
      ),
  });

  if (isPending) return 'Loading...';

  if (error) return `An error has occurred: ${error.message}`;

  return (
    <div>
      <h2 className="text-3xl">{data.name}</h2>
      <p className="text-xl">{data.description}</p>
      <strong className="text-lg text-slate-300">
        👀 {data.subscribers_count}
      </strong>{' '}
      <strong className="text-lg text-blue-300">
        ✨ {data.stargazers_count}
      </strong>{' '}
      <strong className="text-lg text-red-300">🍴 {data.forks_count}</strong>
    </div>
  );
}

export default Example;
