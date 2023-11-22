<script setup>
import { ref, watch, onMounted } from "vue";
import { userAttractionStore } from "@/stores/attraction";
import { storeToRefs } from "pinia";

const attractionStore = userAttractionStore();

const { fullList, desc } = storeToRefs(attractionStore)

var map;
const positions = ref([]); // 위치
const markers = ref([]); // 마커
const layers = ref([]); // 오버레이 객체 관리

watch(
  () => desc.value,
  () => {
    // 이동할 위도 경도 위치를 생성합니다
    // console.log(desc.value.latitude, desc.value.longitude)
    var moveLatLon = new kakao.maps.LatLng(desc.value.latitude, desc.value.longitude);

    // 지도 중심을 부드럽게 이동시킵니다
    // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
    map.panTo(moveLatLon);
    map.setCenter(moveLatLon);
    map.setLevel(3);
  },
  { deep: true }

); // 특정 지점으로 이동

onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    initMap();
  } else {
    const script = document.createElement("script");
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY
      }&libraries=services,clusterer`;
    /* global kakao */
    script.onload = () => kakao.maps.load(() => initMap());
    document.head.appendChild(script);
  }
}); // mounted시 맵

watch(
  () => fullList.value,
  () => {
    setTimeout(() => {
      positions.value = [];
      fullList.value.forEach((attr) => {
        let obj = {};
        obj.latlng = new kakao.maps.LatLng(attr.latitude, attr.longitude);
        obj.title = attr.title; // 관광지 제목
        obj.addr = attr.addr1 // 관광지 주소
        obj.img = attr.firstImage // 관광지 이미지

        positions.value.push(obj);
        loadMarkers();
      });
    }, 100); // 동시 참조시 오류 발생해서 시간차이를 두었음
  },
  { deep: true }
); // 관광지 리스트 변경시 마커 새로 


// 맵 초기화
const initMap = () => {
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  };
  map = new kakao.maps.Map(container, options);

  loadMarkers();
};

const loadMarkers = () => {
  // 현재 표시되어있는 marker들이 있다면 map에 등록된 marker를 제거한다.
  deleteMarkers();
  // 마커 이미지를 생성합니다
  //   const imgSrc = require("@/assets/map/markerStar.png");
  // 마커 이미지의 이미지 크기 입니다
  //   const imgSize = new kakao.maps.Size(24, 35);
  //   const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize);
  // 마커를 생성합니다
  markers.value = [];
  layers.value = [];
  positions.value.forEach((pos) => {
    const marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: pos.latlng, // 마커를 표시할 위치
      title: pos.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됨.
      desc: pos.addr,
      // clickable: true, // // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
      // image: markerImage, // 마커의 이미지
    });
    markers.value.push(marker);

    var overlay = new kakao.maps.CustomOverlay({
      yAnchor: 3,
      position: marker.getPosition()
    });

    // 오버레이 생성
    // 부모 -> 자식
    var content = document.createElement('div');
    content.classList.add("wrap");

    var info = document.createElement('div');
    info.classList.add("info");
    content.appendChild(info);

    var title = document.createElement('div');
    title.classList.add("title");
    title.innerHTML = pos.title;
    info.appendChild(title);
    // content -> info -> title 

    var close = document.createElement('div');
    close.classList.add("close");
    close.onclick = function () {
      overlay.setMap(null);
    };
    title.appendChild(close);
    // title -> close

    var body = document.createElement('div');
    body.classList.add("body");
    info.appendChild(body);
    // info -> body

    var img = document.createElement('div');
    img.classList.add("img");
    body.appendChild(img)
    // body -> img

    var thumbnail = document.createElement('img');
    thumbnail.src = `${pos.img}`;
    thumbnail.width = "73";
    thumbnail.height = "70";
    thumbnail.alt = "이미지 없음";
    img.appendChild(thumbnail);
    // img - src

    var desc = document.createElement('div');
    desc.classList.add("desc");
    body.appendChild(desc);
    // body -> desc

    var ellipsis = document.createElement('div');
    ellipsis.classList.add("ellipsis");
    ellipsis.innerHTML = pos.addr;
    desc.appendChild(ellipsis);
    // desc - > ellipsis

    overlay.setContent(content);
    layers.value.push(overlay)

    kakao.maps.event.addListener(marker, 'click', function () {

      layers.value.forEach((item) => {
        item.setMap(null)
      }) // 클릭 이벤트 발생시 이미 있는 오버레이 모두 제거

      overlay.setMap(map); // 이후 새로 오버레이 세팅
    });


    //   var clusterer = new kakao.maps.MarkerClusterer({
    //   map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체
    //   averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
    //   minLevel: 8, // 클러스터 할 최소 지도 레벨
    // });
    // // 데이터를 가져오기 위해 jQuery를 사용합니다
    // // 데이터를 가져와 마커를 생성하고 클러스터러 객체에 넘겨줍니다

    // // 데이터에서 좌표 값을 가지고 마커를 표시합니다
    // // 마커 클러스터러로 관리할 마커 객체는 생성할 때 지도 객체를 설정하지 않습니다
    // var cluster = markers.value.map(function (position) {
    //   return new kakao.maps.Marker({
    //     position: position.latlng,
    //   });
    // });

    // clusterer.addMarkers(cluster);



  });

  // 4. 지도를 이동시켜주기
  // 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);
  const bounds = positions.value.reduce(
    (bounds, position) => bounds.extend(position.latlng),
    new kakao.maps.LatLngBounds()
  );

  map.setBounds(bounds);
};



const deleteMarkers = () => {
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null));
  }
};
</script>

<template>
  <div id="map"></div>
</template>

<style lang="scss">
body {
  width: 100%;
}

#map {
  width: 100%;
  height: 800px;
}

.wrap {
  position: absolute;
  left: 0;
  bottom: 40px;
  width: 288px;
  height: 132px;
  margin-left: -144px;
  text-align: left;
  overflow: hidden;
  font-size: 12px;
  font-family: 'Malgun Gothic', dotum, '돋움', sans-serif;
  line-height: 1.5;
}

.wrap * {
  padding: 0;
  margin: 0;
}

.wrap .info {
  width: 286px;
  height: 120px;
  border-radius: 5px;
  border-bottom: 2px solid #ccc;
  border-right: 1px solid #ccc;
  overflow: hidden;
  background: #fff;
}

.wrap .info:nth-child(1) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}

.info .title {
  padding: 5px 0 0 10px;
  height: 30px;
  background: #eee;
  border-bottom: 1px solid #ddd;
  font-size: 18px;
  font-weight: bold;
}

.info .close {
  position: absolute;
  top: 10px;
  right: 10px;
  color: #888;
  width: 17px;
  height: 17px;
  background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png');
}

.info .close:hover {
  cursor: pointer;
}

.info .body {
  position: relative;
  overflow: hidden;
}

.info .desc {
  position: relative;
  margin: 13px 0 0 90px;
  height: 75px;
}

.desc .ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.desc .jibun {
  font-size: 11px;
  color: #888;
  margin-top: -2px;
}

.info .img {
  position: absolute;
  top: 6px;
  left: 5px;
  width: 73px;
  height: 71px;
  border: 1px solid #ddd;
  color: #888;
  overflow: hidden;
}

.info:after {
  content: '';
  position: absolute;
  margin-left: -12px;
  left: 50%;
  bottom: 0;
  width: 22px;
  height: 12px;
  background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png')
}

.info .link {
  color: #5085BB;
}
</style>