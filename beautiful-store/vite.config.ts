import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import tailwindcss from "@tailwindcss/vite";
import path from "node:path";

export default defineConfig({

  build:{
    outDir: "../backend/src/main/resources/static",
  },
  plugins: [vue(), tailwindcss()],
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "./src"),
    },
  },
  server: {
    port: 3333,
    open: false,
    proxy: {
      "/api": {
        target: "http://127.0.0.1:33333",
        changeOrigin: true,
      },
    },
  },
});
//^7.0.0
