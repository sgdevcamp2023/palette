export const env = {
  VITE_BASE_SERVER_URL: String(process.env.VITE_BASE_SERVER_URL),
  VITE_CDN_BASE_URL: String(process.env.VITE_CDN_BASE_URL),
  VITE_CLOUD_NAME: String(process.env.VITE_CLOUD_NAME),
  VITE_CLD_API_KEY: String(process.env.VITE_CLD_API_KEY),
  VITE_CLD_PRESET_NAME: String(process.env.VITE_CLD_PRESET_NAME),
  VITE_CLD_SECRET: String(process.env.VITE_CLD_SECRET),
  VITE_CLD_ENVIRONMENT_VARIABLE: String(
    process.env.VITE_CLD_ENVIRONMENT_VARIABLE,
  ),
} as const;
