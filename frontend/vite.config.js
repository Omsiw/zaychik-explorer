import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    host: true,
    port: 3000,
  },
  // --- НАЧАЛО ИЗМЕНЕНИЙ ---
  // Этот блок решает проблему "global is not defined"
  // для таких библиотек, как sockjs-client
  define: {
    'global': {},
  }
  // --- КОНЕЦ ИЗМЕНЕНИЙ ---
})