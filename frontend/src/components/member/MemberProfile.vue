<script setup>
import { ref, computed } from "vue";
import { useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";

const memberStore = useMemberStore();

const { userInfo, refreshToken } = storeToRefs(memberStore);
const { memberDelete } = memberStore
const router = useRouter();

const userForm = ref({
    email: userInfo.value.email,
    password: "",
    passwordAgain: "",
    name: userInfo.value.name,
    profileImageUrl: userInfo.value.profileImageUrl
});

const passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,30}$";

const passwordValidation = computed(() => {
    return !(userForm.value.password == null || userForm.value.password !== userForm.value.passwordAgain || userForm.value.password.trim() === "" || !userForm.value.password.match(passwordRegex));
});

const changeMemberPassword = async () => {
    // 비밀번호 재입력 검사
    if (passwordValidation.value === true) {
        await memberStore.memberChangePassword(userForm)
            .then(() => {
                alert("비밀번호가 변경되었습니다.");
                router.push({ name: "home" });
            });
    }
    else {
        alert("비밀번호가 유효하지 않습니다.");
    }
};

if (refreshToken.value == null) {
    router.push({ name: "signin" });
}

const deleteMember = async () => {
    await memberDelete()
}

const profileImageFormData = new FormData();

const file = ref(null);

const addProfileImage = () => {

};

const clear = () => {
    profileImageFormData.delete("ProfileImage");
};

</script>

<template>
    <div class="container rounded bg-white mt-5 mb-5">
        <div class="row">
            <div class="col-md-3 border-right">
                <div class="d-flex flex-column align-items-center text-center p-3 py-5">
                    <img v-if="userInfo.profileImageUrl != null" class="rounded-circle mt" width="150" height="150"
                        style="margin-bottom: 10%;" :src="userForm.profileImageUrl">
                    <img v-if="file != null" class="rounded-circle mt" width="150" height="150"
                        style="margin-bottom: 10%;" :src="URL.createObjectURL(file.value)">
                    <img v-else class="rounded-circle mt" width="150" height="150" style="margin-bottom: 10%;"
                        src="https://mblogthumb-phinf.pstatic.net/MjAyMTAyMDRfNjIg/MDAxNjEyNDA4OTk5NDQ4.6UGs399-0EXjIUwwWsYg7o66lDb-MPOVQ-zNDy1Wnnkg.m-WZz0IKKnc5OO2mjY5dOD-0VsfpXg7WVGgds6fKwnIg.JPEG.sunny_side_up12/1612312679152%EF%BC%8D2.jpg?type=w800">
                    <div class="font-weight-bold">{{ userForm.name }}</div>
                    <div class="text-black-50">{{ userForm.email }}</div>

                    <div class="information">
                        <button class="btn btn-primary profile-button" @click="$refs.fileRef.click">이미지 선택</button>
                        <input type="file" @change="addProfileImage" ref="fileRef" hidden/>
                    </div>

                    <div class="mt-5 text-center"><button class="btn btn-primary profile-button" type="button"
                            @click="changeProfileImage">프로필 사진 변경</button></div>
                    <div class="mt-5 text-center"><button class="btn btn-danger" type="button"
                            @click="deleteProfileImage">프로필 사진 삭제</button></div>
                </div>
            </div>
            <div class="col-md-5 border-right">
                <div class="p-3 py-5">
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h4 class="text-right">프로필</h4>
                    </div>
                    <div class="row mt-2">
                        <div class="col-md-12"><label class="labels">이름</label><input type="text" class="form-control"
                                placeholder="name" v-model="userForm.name" disabled></div>
                        <div class="col-md-12"><label class="labels">이메일</label><input type="text" class="form-control"
                                placeholder="email" v-model="userForm.email" disabled></div>
                    </div>
                    <div class="row mt-3">

                        <div class="col-md-12"><label class="labels">비밀번호</label><input type="password" class="form-control"
                                placeholder="enter password" v-model.lazy="userForm.password"></div>
                        <div class="col-md-12"><label class="labels">비밀번호 확인</label><input type="password"
                                class="form-control" placeholder="enter password again"
                                v-model.lazy="userForm.passwordAgain"></div>
                        <div v-show="!passwordValidation" class="validation">
                            대문자, 소문자, 숫자, 특수문자를 포함 8자리 이상이어야 합니다.
                        </div>
                    </div>
                    <div class="mt-5 text-center"><button class="btn btn-primary profile-button" type="button"
                            @click="changeMemberPassword">비밀번호 변경</button></div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="p-3 py-5">
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h4 class="text-right">회원 탈퇴</h4>
                    </div>
                    <div class="mt-5 text-center"><button class="btn btn btn-danger" type="button" @click="deleteMember">회원
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

.validation {
    width: 100%;
    margin-top: 0.25rem;
    font-size: .875em;
    color: var(--bs-form-invalid-color);
}
</style>
