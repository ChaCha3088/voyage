<script setup>
import {computed, ref} from "vue";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";

const router = useRouter();
const memberStore = useMemberStore();

const emailRegex = "(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))";
const passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,30}$";

const emailValidation = computed(() => {
    return !(userForm.value.email == null || userForm.value.email.trim() === "" || !userForm.value.email.match(emailRegex));
});

const passwordValidation = computed(() => {
    return !(userForm.value.password == null || userForm.value.password.trim() === "" || !userForm.value.password.match(passwordRegex));
});

const userForm = ref({
    email: "",
    password: "",
});

const signIn = async () => {
    if (emailValidation.value === true && passwordValidation.value === true) {
        await memberStore.memberSignIn(userForm.value)
            .then(() => {
                router.push({ name: "home" });
            });
    }
    else {
        alert("이메일과 비밀번호를 확인해주세요.");
    }
};
</script>

<template>
    <div class="login container mt-5 pt-5 mx-auto max-w-xs" id="login">
        <h1 class="logo-font text-4xl text-center py-8 dark:text-gray-300">| LOGIN |</h1>
        <div id="form">
            <form action=" " class="group" method="POST" novalidate>
                <div v-show="!emailValidation" class="validation">
                    이메일을 확인해주세요.
                </div>
                <div class="form-floating mb-3">
                    <input type="email" class="form-control" id="floatingInput" placeholder="name@example.com"
                           v-model.lazy="userForm.email" @input="emailValidation">
                    <label for="floatingInput">이메일 주소</label>
                </div>
                <div v-show="!passwordValidation" class="validation">
                    대문자, 소문자, 숫자, 특수문자를 포함 8자리 이상이어야 합니다.
                </div>
                <div class="form-floating mb-3">
                    <input type="password" class="form-control" id="floatingPassword" placeholder="Password"
                           v-model="userForm.password" @keyup.enter="signIn">
                    <label for="floatingPassword">비밀번호</label>
                </div>
                <button class="w-100 btn btn-lg btn-primary" type="button" @click="signIn">Sign in</button>
            </form>
        </div>

        <div class="mt-4 flex justify-center items-center">
            <span class="text-gray-500 text-sm mr-2">아이디가 없으신가요? </span>
            <router-link :to="{ name: 'signup' }">회원가입</router-link>
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
