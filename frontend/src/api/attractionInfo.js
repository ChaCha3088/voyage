import { localAxios } from "@/utils/http-commons";

const attractionApi = localAxios();

const url = "/api/attraction";

//local.get(`${url}`, { params: param }).then(success).catch(fail);

const getList = (param, success, fail) => {
    attractionApi.get(`${url}/list`, param).then(success).catch(fail);
  };
  
  const get = (deptno, success, fail) => {
    deptApi.get(`/api/depts/${deptno}`).then(success).catch(fail);
  };
  
  const add = (dept, success, fail) => {
    deptApi.post("/api/depts", dept).then(success).catch(fail);
  };
  
  const modify = (dept, success, fail) => {
    deptApi.put(`/api/depts/${dept.deptno}`, dept).then(success).catch(fail);
  };
  
  const deletes = (deptno, success, fail) => {
    deptApi.delete(`/api/depts/${deptno}`).then(success).catch(fail);
  };
  
  export default { getList, get, add, modify, deletes };