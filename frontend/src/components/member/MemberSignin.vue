<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";

const memberStore = useMemberStore();

const { isLogin } = storeToRefs(memberStore);
const { userSignIn, getUserInfo } = memberStore;


const router = useRouter();

const User = ref({
    email: "",
    password: "",
});

const signIn = async () => {
    await userSignIn(User.value);
    let token = sessionStorage.getItem("accessToken");
    console.log("토큰. ", token);
    console.log("isLogin: ", isLogin);
    if (isLogin) {
        console.log("로그인 성공");
        //getUserInfo(token);
    }
    router.push("/");
};

</script>


<template>
    <div class="login container mt-5 pt-5 mx-auto max-w-xs" id="login">
        <h1 class="logo-font text-4xl text-center py-8 dark:text-gray-300">| LOGIN |</h1>
        <div id="form">
            <form action=" " class="group" method="POST" novalidate>
                <div class="form-floating mb-3">
                    <input type="email" class="form-control" id="floatingInput" placeholder="name@example.com"
                        v-model="User.email">
                    <label for="floatingInput">이메일 주소</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="password" class="form-control" id="floatingPassword" placeholder="Password"
                        v-model="User.password" @keyup.enter="signIn">
                    <label for="floatingPassword">비밀번호</label>
                </div>
                <button class="btn btn-outline-success" type="button" @click="signIn">로그인</button>
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
</style>