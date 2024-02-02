import { splitVendorChunkPlugin, defineConfig } from 'vite';
import path from 'path';
import react from '@vitejs/plugin-react';
import svgr from 'vite-plugin-svgr';
import EnvironmentPlugin from 'vite-plugin-environment';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    react(),
    svgr({
      svgrOptions: {
        typescript: true,
      },
      esbuildOptions: {
        loader: 'tsx',
      },
    }),
    EnvironmentPlugin('all'),
    splitVendorChunkPlugin(),
  ],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
    },
  },
  build: {
    target: 'modules',
    cssMinify: true,
    sourcemap: true,
  },
});
