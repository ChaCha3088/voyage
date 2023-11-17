<script setup>
import { ref } from "vue";
import attractionApi from '@/api/attractionInfo.js'
import contentTypeApi from '@/components/views/contentType.js'

const attractions = ref({})

const params = ref({
    lastId: 0,
    sidoCode: 0,
    contentTypeId: 0,
    title: "",
    pageSize: 0
})

const getAttractionList = () => {
    attractionApi.getList(params.value,
        ({ data }) => {
            attractions.value = data.content;
        },
        () => {
            alert("여행지 목록 조회 실패");
        })
}

const getContentType = () => {
    contentTypeApi.getContentType(
        ({ data }) => {
            console.log(data);
        },
        () => {
            alert("content type 조회 실패");
        })
}

getAttractionList();
</script>

<template>
    <h2>전국 관광지 정보</h2>
    <div id="wrapper-body" class="dark:bg-gray-900 " style="display: flex; justify-content : center;">
        <div class=" col-md-9 col-lg-5">
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
                    <input id="search-keyword" class="form-control me-2" type="search" placeholder="검색어" aria-label="검색어"
                        v-model="params.title" />
                    <button id="btn-search" class="btn btn-outline-success" type="button"
                        @click="getAttraction()">검색</button>
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
    <div class="content_detail__pagination cdp" actpage="1">
        <a href="#!-1" class="cdp_i">prev</a>
        <a href="#!1" class="cdp_i">1</a>
        <a href="#!2" class="cdp_i">2</a>
        <a href="#!3" class="cdp_i">3</a>
        <a href="#!4" class="cdp_i">4</a>
        <a href="#!5" class="cdp_i">5</a>
        <a href="#!6" class="cdp_i">6</a>
        <a href="#!7" class="cdp_i">7</a>
        <a href="#!8" class="cdp_i">8</a>
        <a href="#!9" class="cdp_i">9</a>
        <a href="#!10" class="cdp_i">10</a>
        <a href="#!11" class="cdp_i">11</a>
        <a href="#!12" class="cdp_i">12</a>
        <a href="#!13" class="cdp_i">13</a>
        <a href="#!14" class="cdp_i">14</a>
        <a href="#!15" class="cdp_i">15</a>
        <a href="#!16" class="cdp_i">16</a>
        <a href="#!17" class="cdp_i">17</a>
        <a href="#!18" class="cdp_i">18</a>
        <a href="#!19" class="cdp_i">19</a>
        <a href="#!+1" class="cdp_i">next</a>
    </div>
</template>

<style scoped lang="scss">
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
    margin: auto;
    width: 70%;
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

body {
    background: #333;
    font-family: sans-serif;
    overflow: hidden;
}

@keyframes cdp-in {
    from {
        transform: scale(1.5);
        opacity: 0;
    }

    to {
        transform: scale(1);
        opacity: 1;
    }
}

.cdp {
    position: relative;
    text-align: center;
    padding: 20px 0;
    font-size: 0;
    z-index: 6;
    margin: 50px 0;

    animation: cdp-in 500ms ease both;
    animation-timeout: 200ms;

    &_i {
        font-size: 14px;
        text-decoration: none;

        transition: background 250ms;

        display: inline-block;
        text-transform: uppercase;
        margin: 0 3px 6px;
        height: 38px;
        min-width: 38px;
        border-radius: 38px;
        border: 2px solid rgb(56, 68, 68);
        line-height: 38px;
        padding: 0;
        color: #000;
        font-weight: 700;
        letter-spacing: .03em;
        display: none;

        &:first-child,
        &:last-child {
            padding: 0 16px;
            margin: 0 12px 6px;
        }

        &:last-child,
        &:nth-child(2),
        &:nth-last-child(2) {
            display: inline-block;
        }
    }

    &_i:hover {
        background-color: #787b8d;
        color: #fff;
    }

    &:not([actpage="1"]) &_i:nth-child(1) {
        display: inline-block;
    }
}

@for $i from 1 through 80 {
    .cdp[actpage="#{$i}"] {

        // 3 before
        .cdp_i:nth-child(#{$i - 2}):not(:first-child):not(:nth-child(2)) {
            display: inline-block;
            pointer-events: none;
            color: transparent;
            border-color: transparent;
            width: 50px;

            &:after {
                content: '...';
                color: black;
                font-size: 32px;
                margin-left: -6px;
            }
        }

        // 2 before
        .cdp_i:nth-child(#{$i - 1}):not(:first-child) {
            display: inline-block;
        }

        // before
        .cdp_i:nth-child(#{$i}):not(:first-child) {
            display: inline-block;
        }

        // active
        .cdp_i:nth-child(#{$i + 1}) {
            background-color: #42444e;
            color: #fff;
            display: inline-block;

            +.cdp_i:last-child {
                display: none !important;
            }
        }

        // next
        .cdp_i:nth-child(#{$i + 2}):not(:last-child) {
            display: inline-block;
        }

        // 2 next
        .cdp_i:nth-child(#{$i + 3}):not(:last-child) {
            display: inline-block;
        }

        // 3 next
        .cdp_i:nth-child(#{$i + 4}):not(:last-child):not(:nth-last-child(2)) {
            display: inline-block;
            pointer-events: none;
            color: transparent;
            border-color: transparent;
            width: 50px;

            &:after {
                content: '...';
                color: black;
                font-size: 32px;
                margin-left: -6px;
            }
        }
    }
}
</style>