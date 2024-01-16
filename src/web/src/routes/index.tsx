import { Outlet, Router, Route, RootRoute } from '@tanstack/react-router';

import {
  ChangePasswordPage,
  ChatPage,
  HomePage,
  LoginPage,
  MembershipEntryPage,
  NotificationPage,
  SearchPage,
  SignUpPage,
} from '@/pages';

export const rootRoute = new RootRoute({
  component: () => <Outlet />,
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
const signUpRoute = new Route({
  getParentRoute: () => rootRoute,
  path: '/signup',
  component: () => <SignUpPage />,
});
const changePasswordRoute = new Route({
  getParentRoute: () => rootRoute,
  path: '/change-password',
  component: () => <ChangePasswordPage />,
});

const routeTree = rootRoute.addChildren([
  homeRoute,
  loginRoute,
  notificationRoute,
  chatRoute,
  searchRoute,
  membershipEntryRoute,
  signUpRoute,
  changePasswordRoute,
]);

export const router = new Router({ routeTree });
