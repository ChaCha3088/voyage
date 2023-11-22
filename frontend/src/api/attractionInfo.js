import { localAxios } from "@/utils/http-commons";

const axios = localAxios();

const url = "/api/attraction";

const getList = (param, success, fail) => {
  const searchParams = new URLSearchParams(param);
  axios
    .get(`${url}/list?${searchParams.toString()}`, {
      data: {},
    })
    .then(success)
    .catch(fail);
}; // 관광지 목록 구하기

const getDetail = (contentId, success, fail) => {
  axios
    .get(`${url}/detail/${contentId}`, {
      data: {},
    })
    .then(success)
    .catch(fail);
}; // 특정 관광지의 상세정보 구하기

export default { getList, getDetail };
export { getList, getDetail };
