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

const findByTitleAndSidoCode = (param, success, fail) => {
  axios.get(`${url}/search`, param).then(success).catch(fail);
};

const findByContentId = (contentId, success, fail) => {
  axios.get(`${url}/search/${contentId}`).then(success).catch(fail);
};

const findByContentIdForDes = (contentId, success, fail) => {
  axios.get(`${url}/des/search/${contentId}`).then(success).catch(fail);
};

export default { getList, findByTitleAndSidoCode, findByContentId, findByContentIdForDes };
export { getList, findByTitleAndSidoCode, findByContentId, findByContentIdForDes };
