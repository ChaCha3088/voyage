import { ref } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";
import { jwtDecode } from "jwt-decode";

import { signIn, reIssue, signOut, getMemberInfo } from "@/api/member";
import { httpStatusCode } from "@/utils/http-status";

export const useMemberStore = defineStore("memberStore", () => {
  const router = useRouter();

  const isLogin = ref(false);
  const isLoginError = ref(false);
  const userInfo = ref({
    email: "",
    name: "",
  });
  const isValidToken = ref(false);

  const userSignIn = async (User) => {
    await signIn(
      User,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          let accessToken = response.headers.get("Authorization");
          let refreshToken = response.headers.get("Authorization-refresh");
          console.log("accessToken", accessToken);
          console.log("refreshToken", refreshToken);
          isLogin.value = true;
          isLoginError.value = false;
          isValidToken.value = true;
          sessionStorage.setItem("Authorization", accessToken);
          sessionStorage.setItem("Authorization-refresh", refreshToken);
          console.log("sessiontStorage 저장", isLogin.value);
        } else {
          console.log("로그인 실패");
          isLogin.value = false;
          isLoginError.value = true;
          isValidToken.value = false;
        }
      },
      (error) => {
        console.error(error);
      }
    );
  };

  const getUserInfo = async () => {
    await getMemberInfo(
      (response) => {
        if (response.status === httpStatusCode.OK) {
          userInfo.value.email = response.data.email;
          userInfo.value.name = response.data.name;
          console.log("3. getUserInfo data >> ", response.data);
        } else {
          console.log("유저 정보 없음");
        }
      },
      async (error) => {
        console.error("getUserInfo() error code [토큰 만료] ::: ", error.response.status);
        isValidToken.value = false;

        await userReIssue();
      }
    );
  };

  const userReIssue = async () => {
    console.log("토큰 재발급 >> 기존 토큰 정보 : {}", sessionStorage.getItem("Authorization"));
    const url = window.location.pathname;
    console.log(url);
    await reIssue(
      url,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          let accessToken = response.headers.get("Authorization");
          let refreshToken = response.headers.get("Authorization-refresh");
          console.log("재발급 완료 >> 새로운 토큰 : {}", accessToken);
          console.log("재발급 완료 >> 새로운 리프레시 토큰 : {}", refreshToken);
          sessionStorage.setItem("Authorization", accessToken);
          sessionStorage.setItem("Authorization-refresh", refreshToken);
          isValidToken.value = true;
        }
      },
      async (error) => {
        // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
        if (error.response.status === httpStatusCode.UNAUTHORIZED) {
          console.log("갱신 실패");
          // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
          await signOut(
            userInfo.value.email,
            (response) => {
              if (response.status === httpStatusCode.OK) {
                console.log("리프레시 토큰 제거 성공");
              } else {
                console.log("리프레시 토큰 제거 실패");
              }
              alert("RefreshToken 기간 만료 다시 로그인해 주세요.");
              isLogin.value = false;
              userInfo.value = null;
              isValidToken.value = false;
              router.push({ name: "login" });
            },
            (error) => {
              console.error(error);
              isLogin.value = false;
              userInfo.value = null;
            }
          );
        }
      }
    );
  };

  const userSignOut = async () => {
    await signOut(
      (response) => {
        if (response.status === httpStatusCode.OK) {
          isLogin.value = false;
          //userInfo.value = null;
          isValidToken.value = false;
          console.log("로그아웃");
          sessionStorage.removeItem("Authorization");
          sessionStorage.removeItem("Authorization-refresh");
        } else {
          //console.error("유저 정보 없음");
        }
      },
      (error) => {
        console.log(error);
      }
    );
  };

  return {
    isLogin,
    isLoginError,
    userInfo,
    isValidToken,
    userSignIn,
    getUserInfo,
    userReIssue,
    userSignOut,
  };
});
