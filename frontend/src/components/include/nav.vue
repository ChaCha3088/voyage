<script setup>
import { useMenuStore } from "@/stores/menu";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";

const menuStore = useMenuStore();
const memberStore = useMemberStore();

const { isLogin, userInfo } = storeToRefs(memberStore);
const { menuList } = storeToRefs(menuStore);
const { changeMenuState } = menuStore;

const signout = () => {
  console.log("로그아웃!!!!");
  changeMenuState();
  memberStore.userSignOut()
};


</script>

<template>
  <div class="hello shadow-sm p-1 mb-1 bg-body rounded">
    <div class="logo text-center py-6 pt-10 dark:bg-gray-700">
      <h1 class="logo-font text-4xl text-center py-8 dark:text-gray-300">|
        Voyage |</h1> <!-- <img src="{% static 'img/logo.svg' %}" class="h-20 dark:hidden" alt="Flowbite Logo"/>
            <img src="{% static 'img/logo_white.png' %}" class="h-20 hidden dark:block" alt="Flowbite Logo"/> -->

    </div>
    <nav class="navbar navbar-expand-lg sticky-top " style="background-color: white;">
      <div class="container-fluid">
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarScroll"
          aria-controls="navbarScroll" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarScroll">
          <ul class="navbar-nav me-auto my-2 my-lg-0 navbar-nav-scroll" style="--bs-scroll-height: 100px">
            <li class="nav-item">
              <router-link :to="{ name: 'home' }" class="nav-link">메인페이지</router-link>
            </li>
            <li class="nav-item">
              <router-link :to="{ name: 'attractionInfo' }" class="nav-link">여행 검색</router-link>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">커뮤니티</a>
            </li>
            <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                HELP DESK
              </a>
              <ul class="dropdown-menu">
                <li><a class="dropdown-item" href="#">공지사항</a></li>
                <li><a class="dropdown-item" href="#">FAQ</a></li>
                <li>
                  <hr class="dropdown-divider" />
                </li>
                <li><a class="dropdown-item" href="#">규정</a></li>
              </ul>
            </li>

          </ul>
          <form class="d-flex" role="search">
            <input class="form-control me-2" type="search" placeholder="검색" aria-label="Search" />
            <button class="btn btn-outline-success" type="button">search</button>
          </form>

          <div class="dropdown-center" style="padding-left: 2vw;">
            <li class="nav-item dropdown" style="list-style: none; margin-right: 2vw; clear:both">
              <template v-if="isLogin">
                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                  aria-expanded="false">
                  {{ userInfo.name }} 님
                </a>
                <ul class="dropdown-menu dropdown-menu-end dropdown-menu-lg-start">
                  <template v-for="menu in menuList" :key="menu.routeName">
                    <template v-if="!menu.show">
                      <template v-if="menu.routeName === 'signout'">
                        <li class=" dropdown-item" style="margin-left: 0.7vw;">
                          <router-link to="/" @click.prevent="signout">{{
                            menu.name
                          }} </router-link>
                        </li>
                      </template>
                      <template v-else>
                        <li class=" dropdown-item" style="margin-left: 0.7vw;">
                          <router-link :to="{ name: menu.routeName }">{{
                            menu.name
                          }} </router-link>
                        </li>
                      </template>
                    </template>
                  </template>
                </ul>
              </template>
              <template v-else>
                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                  aria-expanded="false">
                  회원 메뉴
                </a>
                <ul class="dropdown-menu dropdown-menu-end dropdown-menu-lg-start">
                  <li style="margin-left: 0.7vw;"><router-link :to="{ name: 'signup' }">회원가입</router-link></li>
                  <li><a class="dropdown-item" href="#">비밀번호 찾기</a></li>
                  <li>
                    <hr class="dropdown-divider" />
                  </li>
                  <li style="margin-left: 0.7vw;"><router-link :to="{ name: 'login' }">로그인</router-link></li>
                </ul>
              </template>
            </li>
          </div>
        </div>
      </div>
    </nav>
  </div>
</template>

<script>

</script>

<style scoped>
@font-face {
  font-family: 'Yeongdo-Rg';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2202-2@1.0/Yeongdo-Rg.woff') format('woff');
  font-weight: normal;
  font-style: normal;
  font-family: 'Yeongdo-Rg';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2202-2@1.0/Yeongdo-Rg.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}

.hello {
  background-color: white;
}

.logo-font {
  font-family: 'Yeongdo-Rg' !important;
  font-family: 'Yeongdo-Rg' !important;
}

.logo {
  padding: 0.5rem;
  margin-top: 7vh;
  margin-bottom: 3vh;
}

/* ul {
  margin-left: auto;
} */


.search {
  padding-right: 1vw;
}

a {
  text-decoration-line: none;
}

a:link {
  color: black;
}

a:visited {
  color: black;
}

a:hover {
  color: black;
}

a:active {
  color: black
}
</style>