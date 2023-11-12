<script setup>
import { ref } from "vue";
import attractionApi  from '@/api/attractionInfo.js'

const attractions = ref([])

const params = ref([])

const getAttraction = () => {
    attractionApi.getList(params,
        ({ data }) => {
            //data : json 부서목록
            attractions.value = data
        }, ({data}) => {
            console.log(data.value)
            console.log("여행지 목록 조회에 실패")
        })
}

</script>

<template>
    <h2>전국 관광지 정보</h2>
    <div id="wrapper-body" class="dark:bg-gray-900 " style="display: flex; justify-content : center;">
        <div class="col-md-9 col-lg-5">
            <!-- 관광지 검색 start -->
            <form class="d-flex my-3" onsubmit="return false;" role="search">
                <div style="display: flex; justify-content : center;">
                    <select id="search-area" class="form-select me-2">
                        <option value="0" selected>검색 할 지역 선택</option>
                    </select>
                    <select id="search-content-id" class="form-select me-2">
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
                    <input id="search-keyword" class="form-control me-2" type="search" placeholder="검색어" aria-label="검색어" v-model="params.title"/>
                    <button id="btn-search" class="btn btn-outline-success" type="button" @click="getAttraction()">검색</button>
                </div>
            </form>
        </div>
    </div>


    <table>
        <thead>
            <tr>
                <th>대표이미지</th>
				<th>관광지명</th>
				<th>주소</th>
				<th>위도</th>
				<th>경도</th>

            </tr>
        </thead>
        <tbody>
            <tbody v-if="attractions.length == 0">
                    <tr>
                        <td colspan="5">등록된 여행지 정보가 없습니다.</td>
                    </tr>
                </tbody>
                <template v-else>
                    <tr v-for="(attraction, index) in attractions" :key="attractions.contentId">
                        <td><img :src="attraction.firstImage" height="200" width="200"></td>
                        <td>{{ attraction.title }}</td>
                        <td>{{ attraction.addr1 }}</td>
                        <td>{{ attraction.latitude }}</td>
                        <td>{{ attraction.longitude }}</td>
                    </tr>
                </template>
        </tbody>
    </table>
</template>

<style scoped>
body {
    color: #666;
    font: 14px/24px "Open Sans", "HelveticaNeue-Light", "Helvetica Neue Light", "Helvetica Neue", Helvetica, Arial, "Lucida Grande", Sans-Serif;
    font-size: 140%;
}

h2 {
    text-align: center;
    padding: 20px 0;
}

table {
    border-collapse: separate;
    border-spacing: 0;
    width: 100%;
}

th,
td {
    padding: 6px 15px;
}

th {
    background: #42444e;
    color: #fff;
    text-align: left;
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