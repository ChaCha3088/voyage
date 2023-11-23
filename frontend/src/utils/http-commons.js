import axios from "axios";

const { VITE_API_BASE_URL, VITE_ATTRACTION_URL } = import.meta.env;
// local vue api axios instance
function localAxios() {
  const instance = axios.create({
    baseURL: VITE_API_BASE_URL,
    // baseURL: "http://localhost:8080",
    headers: {
      "Content-Type": "application/json;charset=utf-8",
      "Access-Control-Allow-Origin": `http://localhost:8080`,
      "Access-Control-Allow-Credentials": "true",
    },
  });
  return instance;
} // 유저 api용

function attrationAxios() {
  const instance = axios.create({
    baseURL: VITE_ATTRACTION_URL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });
  return instance;
} // 관광지 api용

function profileAxios() {
  const instance = axios.create({
    baseURL: VITE_API_BASE_URL,
    // baseURL: "http://localhost:8080",
    headers: {
      "Content-Type": "multipart/form-data",
      "Access-Control-Allow-Origin": `http://localhost:8080`,
      "Access-Control-Allow-Credentials": "true",
    },
  });
  return instance;
} // 프로필 api용

export { localAxios, attrationAxios, profileAxios };
