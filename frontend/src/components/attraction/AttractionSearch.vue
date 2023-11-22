<script setup>
import { ref, onMounted } from "vue";
import sidoApi from '@/api/code.js'
import { userAttractionStore } from "@/stores/attraction";
import { httpStatusCode } from "@/utils/http-status";

const attractionStore = userAttractionStore();

const { getAttraction } = attractionStore

// const { VITE_OPEN_API_SERVICE_KEY } = import.meta.env;

const code = ref([
]) // 시도 코드 - 지역 순

const params = ref({
    lastId: 0, // 마지막 id, 0으로 일단 보냄
    sidoCode: 0,
    contentTypeId: 0,
    title: "",
    pageSize: 0
}) // 관광지 목록을 얻는 파라미터

const getSidoList = async () => {
    sidoApi.getSidoType(
        (response) => {
            if (response.status === httpStatusCode.OK) {
                code.value = response.data
            }
        },
        (error) => {
            console.log(error)
        }
    );
};

onMounted(() => {
    getSidoList();
    //Math.floor(Math.random() * 8),
    getAttraction(params)
}); // mount시 시도리스트와 초기 관광지 목록 부르기

const search = () => {
    getAttraction(params);
} // 검색 버튼 클릭시 지정된 파라미터로 검색하기


</script>

<template>
    <div id="wrapper-body" class="dark:bg-gray-900 " style="display: flex; justify-content : center;">
        <div class=" col-md-9 col-lg-5">
            <!-- 관광지 검색 start -->
            <form class="d-flex my-3" onsubmit="return false;" role="search">
                <div style="display: flex; justify-content : center;">
                    <select id="search-area" class="form-select me-2" v-model="params.sidoCode">
                        <option value="0" selected>검색 할 지역 선택</option>
                        <option v-for="(code, index) in code" :key="index" :value="code.sidoCode">{{
                            code.sidoName }}
                        </option>
                    </select>
                    <select id="search-content-id" class="form-select me-2" v-model="params.contentTypeId">
                        <option value="0" selected>관광지 유형</option>
                        <option value="12">관광지</option>
                        <option value="14">문화시설</option>
                        <option value="15">축제공연행사</option>
                        <option value="25">여행코스</option>
                        <option value="28">레포츠</option>
                        <option value="32">숙박</option>
                        <option value="38">쇼핑</option>
                        <option value="39">음식점</option>
                    </select>
                    <input id="search-keyword" class="form-control me-2" type="search" placeholder="검색어" aria-label="검색어"
                        v-model="params.title" />
                    <button id="btn-search" class="btn btn-outline-success" type="button" @click="search()">검색</button>
                </div>
            </form>
        </div>
    </div>
</template>
<style scoped>
body {
    color: #666;
    font: 14px/24px "Open Sans", "HelveticaNeue-Light", "Helvetica Neue Light", "Helvetica Neue", Helvetica, Arial, "Lucida Grande", Sans-Serif;
    font-size: 140%;

    background-color: #333;
    font-family: sans-serif;
    overflow: hidden;

}

#wrapper-body {
    width: 100%;
}
</style>

