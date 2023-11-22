import { profileAxios } from "@/utils/http-commons";

const profileAPI = profileAxios();

const profileUrl = "/api/member/profile-image";

async function changeProfile(param, success, fail) {
  profileAPI.defaults.headers["Authorization"] = localStorage.getItem("Authorization");
  const formData = new FormData();

  formData.append("ProfileImage", param);

  await profileAPI
    .post(`${profileUrl}`, formData, {
      headers: { "Content-Type": "multipart/form-data" },
    })
    .then(success)
    .catch(fail);
}

async function deleteProfile(param, success, fail) {
  profileAPI.defaults.headers["Authorization"] = localStorage.getItem("Authorization");

  await profileAPI
    .delete(`${profileUrl}`, {
      data: {},
      headers: {
        "Content-Type": "application/json",
      },
    })
    .then(success)
    .catch(fail);
}

export { changeProfile, deleteProfile };
