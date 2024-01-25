import type { AxiosResponse } from 'axios';

export const handleResponse = async <T = any>(
  promise: Promise<AxiosResponse<T, any>>,
): Promise<T> => {
  const response = await promise;
  return response.data as T;
};

export const createApiWrappers = <T extends Record<string, any>>(
  apis: T,
): {
  [K in keyof T]: T[K] extends (
    ...args: infer A
  ) => Promise<AxiosResponse<infer R, any>>
    ? (...args: A) => Promise<R>
    : never;
} => {
  const mapped: any = {};

  Object.keys(apis).forEach((key) => {
    const method = apis[key];
    mapped[key] = (...args: any[]) => handleResponse(method(...args));
  });
  return mapped;
};
