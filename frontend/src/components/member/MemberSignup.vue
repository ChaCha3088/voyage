<script setup>
import { computed, ref } from "vue";
import { useMemberStore } from "@/stores/member";
import { useRouter } from "vue-router";

const router = useRouter();

const memberStore = useMemberStore();

const emailRegex = "(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))";
const passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,30}$";
// 이메일, 비밀번호 정규식

const emailValidation = computed(() => {
    const result = !(userForm.value.email == null || userForm.value.email.trim() === "" || !userForm.value.email.match(emailRegex));
    console.log("email", result);

    return result;
});

const passwordValidation = computed(() => {
    const result = !(userForm.value.password == null || userForm.value.password !== userForm.value.passwordAgain || userForm.value.password.trim() === "" || !userForm.value.password.match(passwordRegex));
    console.log("password", result);

    return result;
});

const nameValidation = computed(() => {
    const result = !(userForm.value.name == null || userForm.value.name.trim() === "");
    console.log("name", result);

    return result;
});
// 위에서부터 이메일, 비밀번호, 이름 검증

const userForm = ref({
    email: "",
    password: "",
    passwordAgain: "",
    name: ""
});

const signUp = async () => {
    if (emailValidation.value === true && passwordValidation.value === true && nameValidation.value === true) {
        await memberStore.memberSignUp(userForm.value)
            .then(() => {
                router.push({ name: "signin" });
            });
    } // 검증 통과시
    else {
        alert("이메일과 비밀번호를 확인해주세요.");
    }
} // 회원가입
</script>

<template>
    <div class="login container mt-5 pt-5 mx-auto max-w-xs" id="login">
        <h1 class="logo-font text-4xl text-center py-8 dark:text-gray-300">| SignUp |</h1>
        <div id="form">
            <form action=" " class="group" method="POST" novalidate>
                <div v-show="!emailValidation" class="validation">
                    이메일을 확인해주세요.
                </div>
                <div class="form-floating mb-3">
                    <input type="email" class="form-control" id="floatingInput" placeholder="name@example.com"
                        v-model.lazy="userForm.email">
                    <label for="floatingInput">이메일 주소</label>
                </div>
                <div v-show="!passwordValidation" class="validation">
                    대문자, 소문자, 숫자, 특수문자를 포함 8자리 이상이어야 합니다.
                </div>
                <div class="form-floating mb-3">
                    <input type="password" class="form-control" id="floatingPassword" placeholder="Password"
                        v-model.lazy="userForm.password">
                    <label for="floatingPassword">비밀번호</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="password" class="form-control" id="floatingPasswordAgain" placeholder="Password"
                        v-model.lazy="userForm.passwordAgain">
                    <label for="floatingPassword">비밀번호 확인</label>
                </div>
                <div v-show="!nameValidation" class="validation">
                    이름을 입력해주세요.
                </div>
                <div class="form-floating mb-3">
                    <input type="email" class="form-control" id="floatingName" placeholder="홍길동"
                        v-model.lazy="userForm.name">
                    <label for="floatingInput">이름</label>
                </div>
                <button class="btn btn-outline-success" type="button" @click="signUp">회원가입</button>
            </form>
        </div>
        <div class="mt-4 flex justify-center items-center">
            <span class="text-gray-500 text-sm mr-2">이미 아이디가 있으신가요? </span>
            <router-link :to="{ name: 'signin' }">로그인</router-link>
        </div>
    </div>
</template>

<style scoped>
@import '@/assets/base.css';

#login {
    max-width: 1280px;
    margin: 0 auto;
    padding: 2rem;

    font-weight: normal;
    width: 50%;
    height: auto;

    display: grid;
    place-items: center;
}

#form {
    display: inline-block;
    text-align: center;
}

@font-face {
    font-family: 'Yeongdo-Rg';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2202-2@1.0/Yeongdo-Rg.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

.logo-font {
    font-family: 'Yeongdo-Rg' !important;
    padding: 0.5rem;
    margin-top: 1vh;
    margin-bottom: 1.5vh;
}

.group {
    width: 17vw;
    margin-bottom: 1vh;

}

.validation {
    width: 100%;
    margin-top: 0.25rem;
    font-size: .875em;
    color: var(--bs-form-invalid-color);
}
</style>
