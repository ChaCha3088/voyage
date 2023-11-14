import { createRouter, createWebHistory } from "vue-router";
import Main from "../components/views/WelcomeView.vue";
import Attraction from "../components/views/AttractionInfoView.vue";
import Login from "../components/UserLogin.vue";
import Signup from "../components/UserSignup.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: Main,
    },
    {
      path: "/attraction",
      name: "attractionInfo",
      component: Attraction,
    },
    {
      path: "/auth/login",
      name: "login",
      component: Login,
    },
    {
      path: "/auth/signup",
      name: "signup",
      component: Signup,
    },
    // {
    //   path: '/about',
    //   name: 'about',
    //   // route level code-splitting
    //   // this generates a separate chunk (About.[hash].js) for this route
    //   // which is lazy-loaded when the route is visited.
    //   component: () => import('../views/AboutView.vue')
    // }
  ],
});

export default router;
