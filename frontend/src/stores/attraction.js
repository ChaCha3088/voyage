import { computed, ref } from "vue";
import { defineStore } from "pinia";
import { getList, getDetail } from "@/api/attractionInfo.js";
import { httpStatusCode } from "@/utils/http-status";

export const userAttractionStore = defineStore("attractionStore", () => {
  const attractionList = ref([]); // 관광지 목록
  const prev = ref({}); // 이전 파라미터
  const lastId = ref(0);
  // 목록 중 가장 마지막 관광지의 id
  // -> 다음 페이지의 관광지 목록을 받기 위함

  const isDetail = ref(false);
  // 현재 상세정보를 보고 있는지
  // 컴포넌트 변경용

  const selectId = ref(0);
  // 현재 고른 관광지 -> 상세정보 보는 용도

  const desc = ref({});
  // 상세정보

  const fullList = computed(() => {
    return attractionList.value;
  });
  // 관광지 전체리스트 getter

  // 관광지 정보를 얻는 function, parameter 필요
  const getAttraction = (param) => {
    prev.value = param.value; // 이전 파라미터 저장
    getList(
      param.value,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          // api 반환 status는 200
          attractionList.value = response.data;
          // 리스트에 새로 넣기

          if (attractionList.value.length != 0)
            // 검색이 성공해도 길이가 0일 수 있으므로
            lastId.value = attractionList.value[attractionList.value.length - 1].contentId;
          // 리스트의 마지막 요소 id
        }
      },
      (error) => {
        console.error(error);
      }
    );
  };

  const nextAttraction = () => {
    prev.value.lastId = lastId;
    getList(
      // prev에서 lastId만 변경하여 paramter로 보내기
      prev.value,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          response.data.forEach((item) => attractionList.value.push(item));
          // 기존 리스트에 새로운 리스트를 더하기

          if (attractionList.value.length != 0)
            lastId.value = attractionList.value[attractionList.value.length - 1].contentId;
        }
      },
      (error) => {
        console.error(error);
      }
    );
  };

  const detail = (id) => {
    isDetail.value = !isDetail.value;
    selectId.value = id;
    // 검색 결과 <-> 상세정보 창 변경

    getDetail(
      selectId.value,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          desc.value = response.data;
        }
      },
      (error) => {
        console.log(error);
      }
    );
  };

  return {
    isDetail,
    desc,
    fullList,
    getAttraction,
    nextAttraction,
    detail,
  };
});
