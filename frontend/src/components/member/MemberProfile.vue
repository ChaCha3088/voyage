<script setup>
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";

const memberStore = useMemberStore();

const { userInfo, refreshToken } = storeToRefs(memberStore);
const { memberDelete, profileModify, profileRemove } = memberStore
const router = useRouter();

const userForm = ref({
    email: userInfo.value.email,
    password: "",
    passwordAgain: "",
    name: userInfo.value.name,
    profileImageUrl: userInfo.value.profileImageUrl
}); // 유저 정보

onMounted(() => {
    userForm.value = userInfo.value
}); // 유저 정보에 api서 얻은 유저의 정보를 저장

const passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,30}$";
// 비밀번호 정규식

const passwordValidation = computed(() => {
    return !(userForm.value.password == null || userForm.value.password !== userForm.value.passwordAgain || userForm.value.password.trim() === "" || !userForm.value.password.match(passwordRegex));
}); // 비밀번호 적합 체크

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
} // 회원 탈퇴

const file = ref(null); // 미리보기 보여줄 이미지 정보를 저장
const param = ref(null) // 이미지 파일 정보
const addProfileImage = async (event) => {

    const files = event.target?.files
    if (files.length > 0) {
        const temp = files[0]
        // FileReader 객체 : 웹 애플리케이션이 데이터를 읽고, 저장하게 해줌
        const reader = new FileReader()
        param.value = temp
        // load 이벤트 핸들러. 리소스 로딩이 완료되면 실행됨.
        reader.onload = (e) => {
            file.value = e.target.result
        } // ref previewImage 값 변경

        // 컨텐츠를 특정 file에서 읽어옴. 읽는 행위가 종료되면 loadend 이벤트 트리거함 
        // & base64 인코딩된 스트링 데이터가 result 속성에 담김
        reader.readAsDataURL(temp)
    }

};

const clear = () => {
    file.value = null
    param.value = null
}; // 취소를 누르면 이미지 정보 저장한 것 삭제

const changeProfileImage = () => {
    profileModify(param)
    file.value = null
} // 프로필 사진 변경

const deleteProfileImage = () => {
    profileRemove()
} // 프로필 사진 삭제


</script>

<template>
    <div class="container rounded bg-white mt-5 mb-5">
        <div class="row">
            <div class="col-md-3 border-right">
                <div class="d-flex flex-column align-items-center text-center p-3 py-5"> <!-- 맨 오른쪽 프로필 정보 구역-->
                    <img v-if="userForm.profileImageUrl != null" class="rounded-circle mt" width="200" height="200"
                        style="margin-bottom: 10%;" :src="userForm.profileImageUrl"> <!-- 프로필 사진 -->

                    <!-- 회원 정보 -->
                    <div class="font-weight-bold">{{ userForm.name }}</div>
                    <div class="text-black-50" style="margin-bottom: 5vh;">{{ userForm.email }}</div>

                    <!-- 변경할 이미지를 업데이트 했을 때만 보이는 구간 -->
                    <div v-if="file != null" style="flex-direction:column margin-top:3vh">
                        <h3>미리보기</h3>
                        <img class="rounded-circle mt" width="200" height="200" :src="file"> <!-- 프로필 사진 미리보기 -->
                        <div class="mt-5 text-center" style="margin-bottom: 2vh;"><button class="btn btn-danger"
                                type="button" @click="clear">취소</button></div> <!--프로필 사진 올린거 취소-->
                        <div class="mt-5 text-center" style="margin-bottom: 2vh;"><button class="btn btn-primary"
                                type="button" @click="changeProfileImage">프로필 사진
                                변경</button></div>
                    </div>


                    <div class="information"> <!-- 프로필 사진 수정 구간 -->
                        <h4 class="text-right">프로필 사진 변경</h4>
                        <button class="btn btn-primary profile-button" @click="$refs.fileRef.click">이미지 선택</button>
                        <input type="file" accept="image/*" @change="addProfileImage" ref="fileRef" hidden />
                        <div class="mt-5 text-center"><button class="btn btn-danger" type="button"
                                @click="deleteProfileImage">프로필 사진 삭제</button></div>
                    </div>
                </div>
            </div>
            <div class="col-md-5 border-right"> <!-- 가운데 구간, 회원 정보 수정 구간 -->
                <div class="p-3 py-5">
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h4 class="text-right">프로필</h4>
                    </div>
                    <div class="row mt-2"> <!-- 이름과 이메일을 보여주는 구간, 수정 불가-->
                        <div class="col-md-12"><label class="labels">이름</label><input type="text" class="form-control"
                                placeholder="name" v-model="userForm.name" disabled></div>
                        <div class="col-md-12"><label class="labels">이메일</label><input type="text" class="form-control"
                                placeholder="email" v-model="userForm.email" disabled></div>
                    </div>
                    <div class="row mt-3"> <!-- 비밀번호 수정 구간-->
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
            <div class="col-md-4"> <!-- 오른쪽 구간 -->
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
`