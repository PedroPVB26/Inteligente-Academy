import { defineConfig } from 'vite'
import react, { reactCompilerPreset } from '@vitejs/plugin-react'
import babel from '@rolldown/plugin-babel'

// https://vite.dev/config/
export default {
  server: {
    host: true, // permite acesso externo
    port: 5173,  // ou outra porta que você queira
    proxy: {
      '/usuario': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        secure: false
      },
      '/curso': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        secure: false
      },
      '/etiqueta': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        secure: false
      }
    }
  }

}
