import { localAxios } from "@/utils/http-commons";

const axios = localAxios();

const getSidoType = (success, fail) => {
  axios
    .get("/api/sido", {
      data: {},
    })
    .then(success)
    .catch(fail);
}; // 시도 코드 api

const url = "/api/content-type";

const getContentType = (success, fail) => {
  axios.get(url).then(success).catch(fail);
}; // 관광지 유형 코드 api

export default { getContentType, getSidoType };
