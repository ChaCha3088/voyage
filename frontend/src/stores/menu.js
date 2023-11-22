import { ref } from "vue";
import { defineStore } from "pinia";

export const useMenuStore = defineStore("menuStore", () => {
  const menuList = ref([
    { name: "회원가입", show: true, routeName: "signup" },
    { name: "로그인", show: true, routeName: "signin" },
    { name: "프로필", show: false, routeName: "profile" },
    { name: "로그아웃", show: false, routeName: "signout" },
  ]); // 메뉴 리스트, 로그인 로그아웃 상태에 따라 표시된 것이 다름

  const changeMenuState = () => {
    menuList.value = menuList.value.map((item) => ({ ...item, show: !item.show }));
  }; // 로그인, 로그아웃 상태에 따라 show값 변경

  return {
    menuList,
    changeMenuState,
  };
});
