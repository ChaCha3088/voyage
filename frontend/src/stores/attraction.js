import { ref } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";
import { getList } from "@/api/attractionInfo.js";
import { httpStatusCode } from "@/utils/http-status";

export const userAttractionStore = defineStore("attractionStore", () => {
  const list = ref([]);
  const prev = ref({});
  const lastId = ref(0);
  const getAttraction = (param) => {
    prev.value = param.value;
    getList(
      param.value,
      ({ data }) => {
        list.value = data;
        lastId.value = list.value[list.value.length - 1].contentId;
        // console.log(list.value.length);
        // console.log(lastId.value);
        // console.log(list.value);
        // console.log(data);
        // console.log(param);
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

  return {
    list,
    getAttraction,
    nextAttraction,
  };
});
