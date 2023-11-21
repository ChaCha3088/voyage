import { ref } from "vue";
import { defineStore } from "pinia";

export const useMenuStore = defineStore("menuStore", () => {
  const menuList = ref([
    { name: "회원가입", show: true, routeName: "signup" },
    { name: "로그인", show: true, routeName: "login" },
    { name: "프로필", show: false, routeName: "profile" },
    { name: "로그아웃", show: false, routeName: "signout" },
  ]);

  const changeMenuState = () => {
    menuList.value = menuList.value.map((item) => ({ ...item, show: !item.show }));
  };
  return {
    menuList,
    changeMenuState,
  };
});
