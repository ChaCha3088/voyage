import { ref, watch } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";

import { signIn, signUp, reissueJwts, signOut, getMember } from "@/api/member";
import { changeMemberPassword, deleteMember } from "@/api/member.js";
import { changeProfile, deleteProfile } from "@/api/image.js";

import { httpStatusCode } from "@/utils/http-status";

export const useMemberStore = defineStore("member", () => {
  const router = useRouter();

  let userInfo = ref({
    email: "",
    name: "",
    profileImageUrl: "",
  });

  const tokenChange = ref(0);

  const refreshToken = ref(localStorage.getItem("Authorization-refresh"));

  // const setRefreshToken = computed(() => {
  //     refreshToken.value = localStorage.getItem("Authorization-refresh")

  //     return refreshToken.value;
  // }) ;

  const userInit = false;

  watch(
    tokenChange,
    () => {
      refreshToken.value = localStorage.getItem("Authorization-refresh");

      console.log(refreshToken.value);

      if (refreshToken.value != null) {
        console.log("memberGet");
        memberGet();
      }
    },
    { deep: true }
  ); // tokenChange값이 바뀔 때마다(api가 호출될 때마다) refresh토큰 값을 가져오고,
  // 토큰으로 유저정보를 구함

  const memberSignIn = async (userForm) => {
    await signIn(
      userForm,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          let accessToken = response.headers.get("Authorization");
          let refreshToken = response.headers.get("Authorization-refresh");

          localStorage.setItem("Authorization", accessToken);
          localStorage.setItem("Authorization-refresh", refreshToken);
          // 로그인 성공시, 헤더에서 Authorization토큰과 refresh토큰을 가져와서
          // 로컬 스토리지에 저장

          tokenChange.value += 1;
          alert("로그인 성공");
        }
      },
      (error) => {
        alert("로그인 실패" + "\n" + error.response.data);
      }
    );
  };

  const memberSignUp = async (userForm) => {
    await signUp(
      userForm,
      (response) => {
        if (response.status === httpStatusCode.CREATED) {
          alert("회원가입 성공");
        }
      },
      (error) => {
        alert("회원가입 실패" + "\n" + error.response.data);
      }
    );
  };

  const memberGet = async () => {
    await getMember(
      (response) => {
        if (response.status === httpStatusCode.OK) {
          userInfo.value.email = response.data.email;
          userInfo.value.name = response.data.name;
          userInfo.value.profileImageUrl = response.data.profileImageUrl;
        } // 회원 정보를 저장해주기
        // 하나씩 해주어야 함
      },
      async () => {
        await memberSignOut();
      }
    );
  };

  const jwtReissue = async () => {
    const url = window.location.pathname;

    await reissueJwts(
      url,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          let accessToken = response.headers.get("Authorization");
          let refreshToken = response.headers.get("Authorization-refresh");

          localStorage.setItem("Authorization", accessToken);
          localStorage.setItem("Authorization-refresh", refreshToken);

          tokenChange.value += 1;
        }
      },
      async () => {
        localStorage.removeItem("Authorization");
        localStorage.removeItem("Authorization-refresh");

        tokenChange.value += 1;

        alert("RefreshToken 기간 만료 다시 로그인해 주세요.");

        router.push({ name: "SignIn" });
        // 재발급 실패시 토큰 삭제 후, 로그인 페이지로
      }
    );
  };

  const memberSignOut = async () => {
    await signOut(
      (response) => {
        if (response.status === httpStatusCode.OK) {
          localStorage.removeItem("Authorization");
          localStorage.removeItem("Authorization-refresh");
          // 토큰 삭제

          tokenChange.value += 1;
          console.log(userInfo);

          // userInfo 비우기
          userInfo.value.email = "";
          userInfo.value.name = "";
          userInfo.value.profileImageUrl = "";
        }
      },
      (error) => {
        console.log(error);
      }
    );
  };

  const memberChangePassword = async (userForm) => {
    await changeMemberPassword(
      {
        password: userForm.value.password,
        passwordAgain: userForm.value.passwordAgain,
      },
      () => {
        userForm.value.password = "";
        userForm.value.passwordAgain = "";
        alert("비밀번호 수정 완료");
      },
      (error) => {
        userForm.value.password = "";
        userForm.value.passwordAgain = "";
        alert("비밀번호 수정 실패" + "\n" + error.response.data);
      }
    ); // 수정여부에 관계없이 폼 비우기
  };

  const memberDelete = async () => {
    await deleteMember(
      () => {
        localStorage.removeItem("Authorization");
        localStorage.removeItem("Authorization-refresh");
        // 토큰 비우기
        tokenChange.value += 1;
        // userInfo 비우기
        userInfo.value.email = "";
        userInfo.value.name = "";
        userInfo.value.profileImageUrl = "";
        alert("회원 탈퇴 완료");
        router.push("/");
      },
      (error) => {
        alert("회원 탈퇴 실패" + "\n" + error.response.data);
      }
    );
  };

  const profileModify = async (param) => {
    await changeProfile(
      param.value,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          alert("프로필 이미지 변경에 성공하였습니다");
          tokenChange.value += 1;
          router.go(0); // 새로고침
        }
      },
      (error) => {
        console.log(error);
      }
    );
  };

  const profileRemove = async () => {
    await deleteProfile(
      () => {},
      (response) => {
        // catch탭에 있는 이유, 삭제 성공시 api는 204를 반환함
        if (response.status === httpStatusCode.NOCONTENT) {
          alert("프로필 이미지 삭제에 성공하였습니다");
          tokenChange.value += 1;
          router.go(0); // 새로고침
        }
      }
    );
  };

  return {
    userInfo,
    tokenChange,
    refreshToken,
    userInit,
    memberSignIn,
    memberSignUp,
    memberGet,
    jwtReissue,
    memberSignOut,
    memberChangePassword,
    memberDelete,
    profileModify,
    profileRemove,
  };
});
