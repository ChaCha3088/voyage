<script setup>
import { userAttractionStore } from "@/stores/attraction";
import { storeToRefs } from "pinia";

const attractionStore = userAttractionStore();
const { desc } = storeToRefs(attractionStore)
const { detail } = attractionStore

const change = () => {
    detail(desc.value.contentId)
}

</script>

<template>
    <div class="wrapper mb-3">
        <table>
            <thead>
                <tr>
                    <th>세부 내용<button @click="change">이전으로</button></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>
                        <div class="card mb-3" style="width: 22vw;">
                            <img :src="desc.firstImage" class="card-img-top" alt="이미지 없음" width="9rem">
                            <div class="card-body">
                                <h5 class="card-title">{{ desc.title }}</h5>
                                <p class="card-text">{{ desc.addr1 }} {{ desc.addr2 }}</p>
                                <p class="card-text">{{ desc.attractionDescription.overview }}</p>
                            </div>
                        </div>
                    </td>
                </tr>
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