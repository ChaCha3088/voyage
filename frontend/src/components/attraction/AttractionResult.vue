<script setup>
import { ref } from "vue";
const attractions = ref([
    {
        "contentId": 2856806,
        "attractionDetail": null,
        "attractionDescription": null,
        "contentTypeId": 39,
        "title": "카규",
        "addr1": "충청남도 천안시 서북구 불당28길 26",
        "addr2": null,
        "zipcode": null,
        "tel": null,
        "firstImage": "http://tong.visitkorea.or.kr/cms/resource/03/2856803_image2_1.png",
        "firstImage2": null,
        "readcount": null,
        "sidoCode": null,
        "gugunCode": null,
        "latitude": 36.81145213,
        "longitude": 127.1017503,
        "mlevel": null
    },
    {
        "contentId": 2856800,
        "attractionDetail": null,
        "attractionDescription": null,
        "contentTypeId": 39,
        "title": "청미래",
        "addr1": "충청남도 천안시 서북구 공단로 64 김남희보신탕",
        "addr2": null,
        "zipcode": null,
        "tel": null,
        "firstImage": "http://tong.visitkorea.or.kr/cms/resource/97/2856797_image2_1.jpg",
        "firstImage2": null,
        "readcount": null,
        "sidoCode": null,
        "gugunCode": null,
        "latitude": 36.84425712,
        "longitude": 127.1262833,
        "mlevel": null
    },
    {
        "contentId": 2856794,
        "attractionDetail": null,
        "attractionDescription": null,
        "contentTypeId": 39,
        "title": "천안 커피 웍스",
        "addr1": "충청남도 천안시 동남구 서리1길 26",
        "addr2": null,
        "zipcode": null,
        "tel": null,
        "firstImage": "http://tong.visitkorea.or.kr/cms/resource/91/2856791_image2_1.png",
        "firstImage2": null,
        "readcount": null,
        "sidoCode": null,
        "gugunCode": null,
        "latitude": 36.78334108,
        "longitude": 127.2355029,
        "mlevel": null
    },
    {
        "contentId": 2856788,
        "attractionDetail": null,
        "attractionDescription": null,
        "contentTypeId": 39,
        "title": "천안짬뽕작전",
        "addr1": "충청남도 천안시 동남구 통정12로 7",
        "addr2": null,
        "zipcode": null,
        "tel": null,
        "firstImage": "http://tong.visitkorea.or.kr/cms/resource/85/2856785_image2_1.jpg",
        "firstImage2": null,
        "readcount": null,
        "sidoCode": null,
        "gugunCode": null,
        "latitude": 36.77896469,
        "longitude": 127.1289627,
        "mlevel": null
    },
    {
        "contentId": 2856782,
        "attractionDetail": null,
        "attractionDescription": null,
        "contentTypeId": 39,
        "title": "천안조개창고",
        "addr1": "충청남도 천안시 서북구 한들3로 8",
        "addr2": null,
        "zipcode": null,
        "tel": null,
        "firstImage": "http://tong.visitkorea.or.kr/cms/resource/77/2856777_image2_1.jpg",
        "firstImage2": null,
        "readcount": null,
        "sidoCode": null,
        "gugunCode": null,
        "latitude": 36.82422606,
        "longitude": 127.1240702,
        "mlevel": null
    },
    {
        "contentId": 2856776,
        "attractionDetail": null,
        "attractionDescription": null,
        "contentTypeId": 39,
        "title": "천안가야밀면",
        "addr1": "충청남도 천안시 서북구 성환1로 151",
        "addr2": null,
        "zipcode": null,
        "tel": null,
        "firstImage": "http://tong.visitkorea.or.kr/cms/resource/72/2856772_image2_1.jpg",
        "firstImage2": null,
        "readcount": null,
        "sidoCode": null,
        "gugunCode": null,
        "latitude": 36.90813591,
        "longitude": 127.1282758,
        "mlevel": null
    },
    {
        "contentId": 2856775,
        "attractionDetail": null,
        "attractionDescription": null,
        "contentTypeId": 38,
        "title": "블루프린트북",
        "addr1": "충청남도 공주시 제민천1길 55",
        "addr2": null,
        "zipcode": null,
        "tel": null,
        "firstImage": "http://tong.visitkorea.or.kr/cms/resource/92/2757092_image2_1.jpg",
        "firstImage2": null,
        "readcount": null,
        "sidoCode": null,
        "gugunCode": null,
        "latitude": 36.45092171,
        "longitude": 127.1232776,
        "mlevel": null
    },
    {
        "contentId": 2856769,
        "attractionDetail": null,
        "attractionDescription": null,
        "contentTypeId": 39,
        "title": "종로제면소",
        "addr1": "충청남도 천안시 서북구 불당21로 67-12 퍼스트프라자",
        "addr2": null,
        "zipcode": null,
        "tel": null,
        "firstImage": "http://tong.visitkorea.or.kr/cms/resource/65/2856765_image2_1.jpg",
        "firstImage2": null,
        "readcount": null,
        "sidoCode": null,
        "gugunCode": null,
        "latitude": 36.81368532,
        "longitude": 127.1084025,
        "mlevel": null
    },
    {
        "contentId": 2856763,
        "attractionDetail": null,
        "attractionDescription": null,
        "contentTypeId": 39,
        "title": "해우담 본점",
        "addr1": "서울특별시 서초구 강남대로 349 우남빌딩",
        "addr2": null,
        "zipcode": null,
        "tel": null,
        "firstImage": "http://tong.visitkorea.or.kr/cms/resource/62/2856762_image2_1.jpg",
        "firstImage2": null,
        "readcount": null,
        "sidoCode": null,
        "gugunCode": null,
        "latitude": 37.49403025,
        "longitude": 127.0291879,
        "mlevel": null
    },
    {
        "contentId": 2856757,
        "attractionDetail": null,
        "attractionDescription": null,
        "contentTypeId": 39,
        "title": "타쿠미곤",
        "addr1": "서울특별시 서초구 사평대로18길 10 부림빌딩",
        "addr2": null,
        "zipcode": null,
        "tel": null,
        "firstImage": "http://tong.visitkorea.or.kr/cms/resource/56/2856756_image2_1.jpg",
        "firstImage2": null,
        "readcount": null,
        "sidoCode": null,
        "gugunCode": null,
        "latitude": 37.49845968,
        "longitude": 126.9956214,
        "mlevel": null
    }
])

</script>

<template>
    <div class="wrapper">
        <table>
            <thead>
                <tr>
                    <th>대표이미지</th>
                </tr>
            </thead>
            <tbody>
            <tbody v-if="attractions.length == 0">
                <tr>
                    <td colspan="5">등록된 여행지 정보가 없습니다.</td>
                </tr>
            </tbody>
            <template v-else>
                <tr v-for="(attraction) in attractions" :key="attraction.contentId">
                    <td>
                        <div class="card" style="width: 23rem;">
                            <img :src="attraction.firstImage" class="card-img-top" alt="이미지 없음">
                            <div class="card-body">
                                <h5 class="card-title">{{ attraction.title }}</h5>
                                <p class="card-text">{{ attraction.addr1 }}</p>
                            </div>
                        </div>
                    </td>
                </tr>
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

.wrapper{
   -ms-overflow-style: none;
}
.wrapper::-webkit-scrollbar{
  display:none;
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