import { attrationAxios } from "@/utils/http-commons";

const axios = attrationAxios();

const getSidoType = (param, success, fail) => {
  const Keys = new URLSearchParams(param);
  axios.get(`areaCode1?${Keys.toString()}`, param).then(success).catch(fail);
};
export default { getSidoType };
