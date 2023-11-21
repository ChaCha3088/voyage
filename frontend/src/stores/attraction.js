import { ref } from "vue";
import { defineStore } from "pinia";
import { getList, getDetail } from "@/api/attractionInfo.js";
import { httpStatusCode } from "@/utils/http-status";

export const userAttractionStore = defineStore("attractionStore", () => {
  const list = ref([]);
  const prev = ref({});
  const lastId = ref(0);

  const isDetail = ref(false);

  const selectId = ref(0);

  const desc = ref({});

  const getAttraction = (param) => {
    prev.value = param.value;
    getList(
      param.value,
      ({ data }) => {
        console.log("getAttraction");
        console.log(list.value);
        list.value = [];
        data.forEach((item) => list.value.push(item));
        lastId.value = list.value[list.value.length - 1].contentId;
        // console.log(list.value.length);
        // console.log(lastId.value);
        // console.log(list.value);
        // console.log(data);
        // console.log(param);
        console.log(list.value);
      },
      (error) => {
        console.error(error);
      }
    );
  };

  const nextAttraction = () => {
    prev.value.lastId = lastId;
    getList(
      prev.value,
      ({ data }) => {
        data.forEach((item) => list.value.push(item));
        lastId.value = list.value[list.value.length - 1].contentId;
        // console.log(lastId.value);
        // console.log(list);
        // console.log(data);
        // console.log(param);
      },
      (error) => {
        console.error(error);
      }
    );
  };

  const detail = (id) => {
    isDetail.value = !isDetail.value;
    selectId.value = id;

    getDetail(
      selectId.value,
      ({ data }) => {
        // console.log(data);
        desc.value = data;
        // console.log(desc.value.attractionDescription.overview);
      },
      (error) => {
        console.log(error);
      }
    );
  };

  console.log("attraction store created..");
  return {
    list,
    isDetail,
    desc,
    getAttraction,
    nextAttraction,
    detail,
  };
});
