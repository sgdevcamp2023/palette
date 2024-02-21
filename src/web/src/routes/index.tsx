import {
  Outlet,
  Router,
  Route,
  RootRoute,
  NotFoundRoute,
} from '@tanstack/react-router';

import {
  ChangePasswordPage,
  ChatPage,
  ErrorPage,
  HomePage,
  LoginPage,
  MembershipEntryPage,
  NotificationPage,
  SearchPage,
  JoinPage,
  PostEditPage,
  ProfilePage,
  PostDetailPage,
  SearchResultPage,
  MyProfilePage,
} from '@/pages';
import { AsyncBoundary } from '@/components';
import { DEFAULT_PAGE, DEFAULT_PAGE_SIZE } from '@/constants';

export const rootRoute = new RootRoute({
  component: () => (
    <AsyncBoundary pendingFallback={<div>Loading...</div>}>
      <Outlet />
    </AsyncBoundary>
  ),
});

const homeRoute = new Route({
  getParentRoute: () => rootRoute,
  path: '/home',
  component: () => <HomePage />,
});

const loginRoute = new Route({
  getParentRoute: () => rootRoute,
  path: '/login',
  component: () => <LoginPage />,
});
const notificationRoute = new Route({
  getParentRoute: () => rootRoute,
  path: '/notification',
  component: () => <NotificationPage />,
});
const chatRoute = new Route({
  getParentRoute: () => rootRoute,
  path: '/chat',
  component: () => <ChatPage />,
});

const searchRoute = new Route({
  getParentRoute: () => rootRoute,
  path: '/search',
  component: () => <SearchPage />,
});
export const searchResultRoute = new Route({
  getParentRoute: () => rootRoute,
  path: '/search/result',
  component: () => <SearchResultPage />,
  validateSearch: (
    search: Record<string, string>,
  ): {
    keyword: string;
    page?: number;
    size?: number;
    category?: 'all' | 'recent' | 'user' | 'media';
  } => ({
    keyword: search.keyword,
    page: Number.isNaN(Number(search.page))
      ? DEFAULT_PAGE
      : Number(search.page),
    size: Number.isNaN(Number(search.size))
      ? DEFAULT_PAGE_SIZE
      : Number(search.size),
    category: (search.category as 'all' | 'recent' | 'user' | 'media') ?? 'all',
  }),
});

const membershipEntryRoute = new Route({
  getParentRoute: () => rootRoute,
  path: '/',
  component: () => <MembershipEntryPage />,
});
const joinRoute = new Route({
  getParentRoute: () => rootRoute,
  path: '/join',
  component: () => <JoinPage />,
});
const changePasswordRoute = new Route({
  getParentRoute: () => rootRoute,
  path: '/change-password',
  component: () => <ChangePasswordPage />,
});

const postRoute = new Route({
  getParentRoute: () => rootRoute,
  path: '/post',
});

export const postDetailRoute = new Route({
  getParentRoute: () => postRoute,
  path: '/$postId',
  component: () => <PostDetailPage />,
  parseParams: (parse): { postId: string } => ({
    postId: parse.postId || '1',
  }),
});

export const profileRoute = new Route({
  getParentRoute: () => rootRoute,
  path: '/profile/$userId',
  component: () => <ProfilePage />,
  parseParams: (parse): { userId: string } => ({
    userId: parse.userId || '1',
  }),
});

const myProfileRoute = new Route({
  getParentRoute: () => rootRoute,
  path: '/profile/me',
  component: () => <MyProfilePage />,
});

export const editPostRoute = new Route({
  getParentRoute: () => postRoute,
  path: '/edit',
  component: () => <PostEditPage />,
  validateSearch: (search: Record<string, string>): { postId?: string } => ({
    postId: search.postId,
  }),
});

const notFoundRoute = new NotFoundRoute({
  getParentRoute: () => rootRoute,
  component: () => (
    <AsyncBoundary>
      <ErrorPage />
    </AsyncBoundary>
  ),
});

const routeTree = rootRoute.addChildren([
  homeRoute,
  loginRoute,
  notificationRoute,
  chatRoute,
  searchRoute.addChildren([searchResultRoute]),
  membershipEntryRoute,
  joinRoute,
  changePasswordRoute,
  profileRoute,
  myProfileRoute,
  postRoute.addChildren([editPostRoute, postDetailRoute]),
]);

export const router = new Router({ routeTree, notFoundRoute });
