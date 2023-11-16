<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import MemberApi from "@/api/member.js"

const router = useRouter();

const User = ref({
    email: "",
    password: "",
    passwordAgain: "",
    name: ""
});

const signUp = () => {
    MemberApi.signUp(User.value,
        ({ data }) => {

        }, () => {
            console.log("회원가입에 실패")
        })
    router.push("/");
}

</script>


<template>
    <div class="login container mt-5 pt-5 mx-auto max-w-xs" id="login">
        <h1 class="logo-font text-4xl text-center py-8 dark:text-gray-300">| SignUp |</h1>
        <div id="form">
            <form action=" " class="group" method="POST" novalidate>
                <div class="form-floating mb-3">
                    <input type="email" class="form-control" id="floatingInput" placeholder="name@example.com"
                        v-model="User.email">
                    <label for="floatingInput">이메일 주소</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="password" class="form-control" id="floatingPassword" placeholder="Password"
                        v-model="User.password">
                    <label for="floatingPassword">비밀번호</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="password" class="form-control" id="floatingPasswordAgain" placeholder="Password"
                        v-model="User.passwordAgain">
                    <label for="floatingPassword">비밀번호 확인</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="email" class="form-control" id="floatingName" placeholder="홍길동" v-model="User.name">
                    <label for="floatingInput">이름</label>
                </div>
                <button class="btn btn-outline-success" type="button" @click="signUp">회원가입</button>
            </form>
        </div>
        <div class="mt-4 flex justify-center items-center">
            <span class="text-gray-500 text-sm mr-2">이미 아이디가 있으신가요? </span>
            <router-link :to="{ name: 'login' }">로그인</router-link>
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
</style>