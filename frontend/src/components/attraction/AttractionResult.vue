<script setup>
import { ref, watch, watchEffect } from "vue";
import { userAttractionStore } from "@/stores/attraction";
import { storeToRefs } from "pinia";

const attractionStore = userAttractionStore();

const { list, isDetail } = storeToRefs(attractionStore)
const { nextAttraction, detail } = attractionStore

console.log("Result.vue")
console.log(attractionStore.list)
console.log(list)

// const attractions = ref([
// ])

// // console.log(attractions.value[attractions.value.length - 1].contentId)

// watch(
//     () => list.value,
//     (newParam) => {
//         console.log(newParam)
//         attractions.value = list.value
//         console.log(attractions.value)
//     }
// )

// watch(() => attractions.value, () => {
//     console.log("Result watch")
//     console.log(attractions.value.length)
// }, { deep: true, immediate: true })

// watch(
//     () => isDetail.value,
//     (newParam) => {
//         attractions.value = list.value
//     }
// )

const more = () => {
    nextAttraction()
}

const change = (id) => {
    detail(id)
}
</script>

<template>
    <div class="wrapper mb-3">
        <table>
            <thead>
                <tr>
                    <th>검색 결과</th>
                </tr>
            </thead>
            <tbody>
            <tbody v-if="list.length == 0">
                <tr>
                    <td colspan="5">검색 결과가 없습니다.</td>
                </tr>
            </tbody>
            <template v-else>
                <tr v-for="(item) in list" :key="item.contentId">
                    <td>
                        <div class="card mb-3" style="width: 22vw;">
                            <img :src="item.firstImage" class="card-img-top" alt="이미지 없음" width="9rem">
                            <div class="card-body">
                                <h5 class="card-title" @click="change(item.contentId)">{{ item.title }}</h5>
                                <p class="card-text">{{ item.addr1 }} {{ item.addr2 }}</p>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr><button class="w-100 btn btn-lg btn-primary" type="button" @click="more">더보기</button></tr>
            </template>
            </tbody>
        </table>
    </div>
</template>
<style scoped>
.wrapper {
    height: 800px;
    overflow: auto;
}

table {
    border-collapse: collapse;
    border-spacing: 0;
    width: 100%;
    height: 800px;
}

.wrapper {
    -ms-overflow-style: none;
}

.wrapper::-webkit-scrollbar {
    display: none;
}

th,
td {
    padding: 6px 15px;
}

th {
    background: #42444e;
    color: #fff;
    text-align: left;
    position: sticky;
    top: 0px;
}

tr:first-child th:first-child {
    border-top-left-radius: 6px;
}

tr:first-child th:last-child {
    border-top-right-radius: 6px;
}

td {
    border-right: 1px solid #c6c9cc;
    border-bottom: 1px solid #c6c9cc;
}

td:first-child {
    border-left: 1px solid #c6c9cc;
}

tr:nth-child(even) td {
    background: #eaeaed;
}

tr:last-child td:first-child {
    border-bottom-left-radius: 6px;
}

tr:last-child td:last-child {
    border-bottom-right-radius: 6px;
}
</style>