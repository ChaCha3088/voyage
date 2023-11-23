import { createRouter, createWebHistory } from "vue-router";
import Main from "../components/views/WelcomeView.vue";
import Attraction from "../components/views/AttractionInfoView.vue";
import SignIn from "../components/member/MemberSignin.vue";
import Signup from "../components/member/MemberSignup.vue";
import Profile from "../components/member/MemberProfile.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: Main,
    }, // 메인
    {
      path: "/attraction",
      name: "attractionInfo",
      component: Attraction,
    }, // 관광지 검색
    {
      path: "/signin",
      name: "signin",
      component: SignIn,
    }, // 로그인
    {
      path: "/signup",
      name: "signup",
      component: Signup,
    }, // 회원가입
    {
      path: "/profile",
      name: "profile",
      component: Profile,
    }, // 프로필
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
