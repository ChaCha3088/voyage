import { localAxios } from "@/utils/http-commons";

const memberAPI = localAxios();

const authUrl = "/api/auth";

async function signIn(param, success, fail) {
  await memberAPI.post(`${authUrl}/signin/v1`, param).then(success).catch(fail);
}

async function signUp(param, success, fail) {
  await memberAPI.post(`${authUrl}/signup/v1`, param).then(success).catch(fail);
}

async function reissueJwts(param, success, fail) {
  const redirectUrl = new URLSearchParams(param);

  memberAPI.defaults.headers["Authorization-refresh"] =
    localStorage.getItem("Authorization-refresh");

  await memberAPI
    .get(`${authUrl}/reissue/v1?redirectUrl=${redirectUrl.toString()}`, {
      data: {},
    })
    .then(success)
    .catch(fail);
}

async function signOut(success, fail) {
  memberAPI.defaults.headers["Authorization-refresh"] =
    localStorage.getItem("Authorization-refresh");

  await memberAPI
    .get(`${authUrl}/signout/v1`, {
      data: {},
    })
    .then(success)
    .catch(fail);
}

async function getMember(success, fail) {
  memberAPI.defaults.headers["Authorization"] = localStorage.getItem("Authorization");

  await memberAPI
    .get(`/api/member`, {
      data: {},
    })
    .then(success)
    .catch(fail);
}

async function changeMemberPassword(param, success, fail) {
  memberAPI.defaults.headers["Authorization"] = localStorage.getItem("Authorization");

  await memberAPI.put(`/api/member/password`, param).then(success).catch(fail);
}

async function deleteMember(success, fail) {
  memberAPI.defaults.headers["Authorization"] = localStorage.getItem("Authorization");

  await memberAPI
    .delete(`/api/member`, {
      data: {},
    })
    // .get(`${url}/signout/v1`)
    .then(success)
    .catch(fail);
}

export { signIn, signUp, reissueJwts, signOut, getMember, changeMemberPassword, deleteMember };
