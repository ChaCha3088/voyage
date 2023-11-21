import axios from "axios";

const { VITE_API_BASE_URL, VITE_ATTRACTION_URL, VITE_WETHER_MIDDLE_URL } = import.meta.env;
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
}

function attrationAxios() {
  const instance = axios.create({
    baseURL: VITE_ATTRACTION_URL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });
  return instance;
}

function weatherAxios() {
  const instance = axios.create({
    baseURL: VITE_WETHER_MIDDLE_URL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });
  return instance;
}

export { localAxios, attrationAxios, weatherAxios };
