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
} from '@/pages';
import { AsyncBoundary } from '@/components';

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

export const profileRoute = new Route({
  getParentRoute: () => rootRoute,
  path: '/profile/$userId',
  component: () => <ProfilePage />,
});

export const editPostRoute = new Route({
  getParentRoute: () => postRoute,
  path: '/edit',
  component: () => <PostEditPage />,
  validateSearch: (search: Record<string, string>): { postId?: string } => ({
    postId: search.postId || '0',
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
  searchRoute,
  membershipEntryRoute,
  joinRoute,
  changePasswordRoute,
  profileRoute,
  postRoute.addChildren([editPostRoute]),
]);

export const router = new Router({ routeTree, notFoundRoute });
