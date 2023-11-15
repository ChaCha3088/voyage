import { localAxios } from "@/utils/http-commons";

const memberApi = localAxios();

const url = "/api/auth";

//local.get(`${url}`, { params: param }).then(success).catch(fail);

// const getList = (param, success, fail) => {
//   const searchParams = new URLSearchParams(param);
//   userApi.get(`${url}/list?${searchParams.toString()}`).then(success).catch(fail);
// };

const signIn = (param, success, fail) => {
  memberApi
    .post(`${url}/signin/v1`, param, {
      header: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": `http://localhost:8080`,
        "Access-Control-Allow-Credentials": "true",
      },
    })
    .then(success)
    .catch(fail);
};

const signUp = (param, success, fail) => {
  memberApi.post(`${url}/signup/v1`, param).then(success).catch(fail);
};

export default { signIn, signUp };
