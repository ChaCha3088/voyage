<script setup>
import { shallowRef } from "vue"
import { storeToRefs } from "pinia";
import { userAttractionStore } from "@/stores/attraction";

const attractionStore = userAttractionStore();

import search from '@/components/attraction/AttractionSearch.vue'
import kakaomap from '@/components/attraction/AttractionMap.vue'
import result from '@/components/attraction/AttractionResult.vue'
import detail from '@/components/attraction/AttractionDetail.vue'
const { isDetail } = storeToRefs(attractionStore)

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
                <result v-show="!isDetail" />
                <detail v-if="isDetail" />
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
}

#right {
    width: 25vw;
}
</style>