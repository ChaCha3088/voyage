import { localAxios } from "@/utils/http-commons";

const attractionApi = localAxios();

const url = "/api/attraction";

//local.get(`${url}`, { params: param }).then(success).catch(fail);

const getList = (param, success, fail) => {
  console.log("getList");
  const searchParams = new URLSearchParams(param);
  attractionApi.get(`${url}/list?${searchParams.toString()}`).then(success).catch(fail);
};

const findByTitleAndSidoCode = (param, success, fail) => {
  attractionApi.get(`${url}/search`, param).then(success).catch(fail);
};

const findByContentId = (contentId, success, fail) => {
  attractionApi.get(`${url}/search/${contentId}`).then(success).catch(fail);
};

const findByContentIdForDes = (contentId, success, fail) => {
  attractionApi.get(`${url}/des/search/${contentId}`).then(success).catch(fail);
};

export default { getList, findByTitleAndSidoCode, findByContentId, findByContentIdForDes };
