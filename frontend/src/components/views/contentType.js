import { localAxios } from "@/utils/http-commons";

const axios = localAxios();

const url = "/api/content-type";

const getContentType = (success, fail) => {
  axios.get(url).then(success).catch(fail);
};
export default { getContentType };
