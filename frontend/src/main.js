import "@/assets/main.css";

import { createApp } from "vue";
import { createPinia } from "pinia";
import App from "./App.vue";
import router from "./router";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";


import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap";

const app = createApp(App);
const pinia = createPinia();

pinia.use(piniaPluginPersistedstate);

app.use(router);
app.use(pinia);


app.mount("#app");
