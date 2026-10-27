import { defineConfig } from 'vitest/config'
import vue from '@vitejs/plugin-vue'

// The frontend calls /api/... and Vite forwards it to the Spring Boot API on port 8000.
// This avoids CORS problems while developing.
export default defineConfig({
  plugins: [vue()],
  server: {
    port: 5173,
    proxy: {
      '/api': {
        target: 'http://127.0.0.1:8000',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ''),
      },
    },
  },
  test: {
    environment: 'jsdom',
    globals: true,
  },
})
