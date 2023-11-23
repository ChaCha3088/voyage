<script setup>
import { storeToRefs } from "pinia";
import { useAttractionStore } from "@/stores/attraction"
import search from '@/components/attraction/AttractionSearch.vue'
import kakaomap from '@/components/attraction/AttractionMap.vue'
import result from '@/components/attraction/AttractionResult.vue'
import detail from '@/components/attraction/AttractionDetail.vue'
const attractionStore = useAttractionStore();
const { isDetail } = storeToRefs(attractionStore)

</script>

<template>
    <div id="wrap">
        <h2>전국 관광지 정보</h2>
        <search />
        <div id="info">
            <div id="left">
                <kakaomap /> <!-- 지도 -->
            </div>
            <div id="right"> <!-- 검색 결과 / 상세 정보-->
                <result v-show="!isDetail" />
                <detail v-if="isDetail" />
            </div> <!-- isDetail 값에 따라 컴포넌트 변경-->
            <!-- result가 show인 이유, detail에서 나가더라도 이전 검색 결과를 남기기 위함 -->
            <!-- 이 때문인지 작동은 하지만 오류 메시지가 발생하긴 함-->
        </div>
    </div>
</template>

<style scoped lang="scss">
#wrap {
    margin-top: 5vh;
}

h2 {
    text-align: center;
    padding: 20px 0px 0px 0px;
}

body {
    background: #333;
    font-family: sans-serif;
    overflow: hidden;
}

// 맵 - 검색(& 상세 정보 배치)
#info {
    display: flex;
}

#left {
    width: 75vw;
}

#right {
    width: 25vw;
}
</style>