import { createApp } from "vue";
import "./style.css";
import App from "./App.vue";
import { router } from "./router";
import bubbleCursorPlugin from "./utils/bubbleCursor";
import { createPinia } from "pinia";

const app = createApp(App);
const pinia = createPinia();
app.use(router);
app.use(pinia);
app.use(bubbleCursorPlugin);
app.mount("#app");
