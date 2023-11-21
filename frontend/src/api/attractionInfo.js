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
};

const getDetail = (contentId, success, fail) => {
  axios
    .get(`${url}/detail/${contentId}`, {
      data: {},
    })
    .then(success)
    .catch(fail);
};

export default { getList, getDetail };
export { getList, getDetail };
