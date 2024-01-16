import { Outlet, Router, Route, RootRoute } from '@tanstack/react-router';

import {
  ChatPage,
  HomePage,
  LoginPage,
  MembershipEntryPage,
  NotificationPage,
  SearchPage,
} from '@/pages';

export const rootRoute = new RootRoute({
  component: () => <Outlet />,
});

const homeRoute = new Route({
  getParentRoute: () => rootRoute,
  path: '/',
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
  path: '/entry',
  component: () => <MembershipEntryPage />,
});

const routeTree = rootRoute.addChildren([
  homeRoute,
  loginRoute,
  notificationRoute,
  chatRoute,
  searchRoute,
]);

export const router = new Router({ routeTree });
