<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
import MemberApi from "@/api/member.js"

const memberStore = useMemberStore();

const { userInfo, isLogin, isValidToken } = storeToRefs(memberStore);

const router = useRouter();

const User = ref({
    email: userInfo.value.email,
    password: "",
    passwordAgain: "",
    name: userInfo.value.name
});

const modify = () => {

    MemberApi.modifyMemberInfo({
        password: User.value.password,
        passwordAgain: User.value.passwordAgain
    }
        , ({ response }) => {

        }, (error) => {
            console.log(User.value)
            console.error(error);
            console.log("수정에 실패")
        })
    router.push("/")
}


const deletes = async () => {

    await MemberApi.deleteMemberInfo(
        ({ response }) => {
            isLogin.value = false;
            //userInfo.value = null;
            isValidToken.value = false;
            console.log("탈퇴");
            sessionStorage.removeItem("Authorization");
            sessionStorage.removeItem("Authorization-refresh");

        }, (error) => {
            console.error(error)
            console.log("탈퇴에 실패")
        })
    router.replace("/")
}

if (!isLogin.value) {
    router.push("/login")
}

</script>

<template>
    <div class="container rounded bg-white mt-5 mb-5">
        <div class="row">
            <div class="col-md-3 border-right">
                <div class="d-flex flex-column align-items-center text-center p-3 py-5">
                    <img class="rounded-circle mt" width="150" height="150" style="margin-bottom: 10%;"
                        src="https://mblogthumb-phinf.pstatic.net/MjAyMTAyMDRfNjIg/MDAxNjEyNDA4OTk5NDQ4.6UGs399-0EXjIUwwWsYg7o66lDb-MPOVQ-zNDy1Wnnkg.m-WZz0IKKnc5OO2mjY5dOD-0VsfpXg7WVGgds6fKwnIg.JPEG.sunny_side_up12/1612312679152%EF%BC%8D2.jpg?type=w800">
                    <div class="font-weight-bold">{{ User.name }}</div>
                    <div class="text-black-50">{{ User.email }}</div>
                </div>
            </div>
            <div class="col-md-5 border-right">
                <div class="p-3 py-5">
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h4 class="text-right">프로필</h4>
                    </div>
                    <div class="row mt-2">
                        <div class="col-md-12"><label class="labels">이름</label><input type="text" class="form-control"
                                placeholder="name" v-model="User.name" disabled></div>
                        <div class="col-md-12"><label class="labels">이메일</label><input type="text" class="form-control"
                                placeholder="email" v-model="User.email" disabled></div>
                    </div>
                    <div class="row mt-3">

                        <div class="col-md-12"><label class="labels">비밀번호</label><input type="password" class="form-control"
                                placeholder="enter password" v-model="User.password"></div>
                        <div class="col-md-12"><label class="labels">비밀번호 확인</label><input type="password"
                                class="form-control" placeholder="enter password again" v-model="User.passwordAgain"></div>
                    </div>
                    <div class="mt-5 text-center"><button class="btn btn-primary profile-button" type="button"
                            @click="modify">프로필
                            변경</button></div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="p-3 py-5">
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h4 class="text-right">회원 탈퇴</h4>
                    </div>
                    <div class="mt-5 text-center"><button class="btn btn btn-danger" type="button" @click="deletes">회원
                            탈퇴</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
body {
    background: rgb(99, 39, 120)
}

.form-control:focus {
    box-shadow: none;
    border-color: #BA68C8
}

.profile-button {
    background: rgb(99, 39, 120);
    box-shadow: none;
    border: none
}

.profile-button:hover {
    background: #682773
}

.profile-button:focus {
    background: #682773;
    box-shadow: none
}

.profile-button:active {
    background: #682773;
    box-shadow: none
}

.back:hover {
    color: #682773;
    cursor: pointer
}

.labels {
    font-size: 11px
}

.add-experience:hover {
    background: #BA68C8;
    color: #fff;
    cursor: pointer;
    border: solid 1px #BA68C8
}
</style>