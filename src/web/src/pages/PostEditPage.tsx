import { editPostRoute } from '@/routes';

function PostEditPage() {
  const search = editPostRoute.useSearch();

  return <div>POST EDIT{search?.postId}</div>;
}

export default PostEditPage;
