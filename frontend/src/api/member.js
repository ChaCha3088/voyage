import { localAxios } from "@/utils/http-commons";

const memberApi = localAxios();

const url = "/api/auth";

//local.get(`${url}`, { params: param }).then(success).catch(fail);

// const getList = (param, success, fail) => {
//   const searchParams = new URLSearchParams(param);
//   userApi.get(`${url}/list?${searchParams.toString()}`).then(success).catch(fail);
// };

async function signIn(param, success, fail) {
  await memberApi
    .post(`${url}/signin/v1`, param, {
      header: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": `http://localhost:8080`,
        "Access-Control-Allow-Credentials": "true",
      },
    })
    .then(success)
    .catch(fail);
}

function signUp(param, success, fail) {
  memberApi
    .post(`${url}/signup/v1`, param, {
      header: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": `http://localhost:8080`,
        "Access-Control-Allow-Credentials": "true",
      },
    })
    .then(success)
    .catch(fail);
}

async function reIssue(param, success, fail) {
  const reUrl = new URLSearchParams(param);

  memberApi.defaults.headers["refreshToken"] = sessionStorage.getItem("refreshToken");

  await memberApi
    .get(`${url}/reissue/v1?${reUrl}`, {
      header: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": `http://localhost:8080`,
        "Access-Control-Allow-Credentials": "true",
      },
    })
    .then(success)
    .catch(fail);
}

async function signOut(success, fail) {
  memberApi.defaults.headers["refreshToken"] = sessionStorage.getItem("refreshToken");

  memberApi
    .get(`${url}/signout/v1`, {
      header: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": `http://localhost:8080`,
        "Access-Control-Allow-Credentials": "true",
      },
    })
    .then(success)
    .catch(fail);
}

export { signIn, reIssue, signOut };
export default { signUp };