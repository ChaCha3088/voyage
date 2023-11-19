<script setup>
import { ref } from "vue";
import attractionApi from '@/api/attractionInfo.js'
import contentTypeApi from '@/components/views/contentType'
import search from '@/components/attraction/AttractionSearch.vue'
import kakaomap from '@/components/attraction/AttractionMap.vue'
import result from '@/components/attraction/AttractionResult.vue'

const { VITE_OPEN_API_SERVICE_KEY } = import.meta.env;

const attractions = ref({})


const code = ref({})

const getAttractionList = () => {
    attractionApi.getList(params.value,
        ({ data }) => {
            attractions.value = data.content;
        },
        (error) => {
            console.error(error)
            alert("여행지 목록 조회 실패");
        })
}

const getContentType = () => {
    contentTypeApi.getContentType(
        ({ data }) => {
            console.log(data);
        },
        (error) => {
            console.error(error)
            alert("content type 조회 실패");
        })
}

</script>

<template>
    <div id="wrap">
        <h2>전국 관광지 정보</h2>
        <search />
        <div id="info">
            <div id="left">
                <kakaomap />
            </div>
            <div id="right">
                <result />
            </div>
        </div>
    </div>
</template>

<style scoped lang="scss">
h2 {
    text-align: center;
    padding: 20px 0px 0px 0px;
}

body {
    background: #333;
    font-family: sans-serif;
    overflow: hidden;
}

#info {
    display: flex;
}

#left {
    width: 75vw;
    background-color: cyan;
}

#right {
    width: 25vw;
}
</style>