<script setup>
import { useRouter } from "vue-router";
import { useMenuStore } from "@/stores/menu";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import { onMounted } from "vue";

const router = useRouter();

const menuStore = useMenuStore();
const memberStore = useMemberStore();

const { userInfo, refreshToken } = storeToRefs(memberStore);
const { menuList } = storeToRefs(menuStore);
const { memberGet } = memberStore

const signOut = () => {
    memberStore.memberSignOut();
};

onMounted(() => {
    if (refreshToken.value != null) {
        memberGet()
    }

}); // 로그인 상태서 새로고침시 이름을 불러오기 위함

</script>

<template>
    <div class="hello shadow-sm p-1 mb-1 bg-body rounded">
        <!-- <div class="logo text-center py-6 pt-10 dark:bg-gray-700">
          <h1 class="logo-font text-4xl text-center py-8 dark:text-gray-300">|
            Voyage |</h1>
        </div> -->
        <nav id="nav" class="navbar navbar-expand-lg fixed-top ">
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
                            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                                aria-expanded="false">
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
                            <div v-show="refreshToken != null">
                                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                                    aria-expanded="false">
                                    <img v-if="userInfo.profileImageUrl != null" class="rounded-circle mt" width="45"
                                        height="45" style="margin-bottom: 10%;" :src="userInfo.profileImageUrl">{{
                                            userInfo.name }} 님
                                </a>
                                <ul class="dropdown-menu dropdown-menu-end dropdown-menu-lg-start">
                                    <template v-for="menu in menuList" :key="menu.routeName">
                                        <template v-if="!menu.show">
                                            <template v-if="menu.routeName === 'signout'">
                                                <li class=" dropdown-item" style="margin-left: 0.7vw;">
                                                    <router-link to="/" @click.prevent="signOut">{{ menu.name }}
                                                    </router-link>
                                                </li>
                                            </template>
                                            <template v-else>
                                                <li class=" dropdown-item" style="margin-left: 0.7vw;">
                                                    <router-link :to="{ name: menu.routeName }">{{ menu.name }}
                                                    </router-link>
                                                </li>
                                            </template>
                                        </template>
                                    </template>
                                </ul>
                            </div>
                            <div v-show="refreshToken == null">
                                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                                    aria-expanded="false">
                                    회원 메뉴
                                </a>
                                <ul class="dropdown-menu dropdown-menu-end dropdown-menu-lg-start">
                                    <template v-for="menu in menuList" :key="menu.routeName">
                                        <template v-if="menu.show">
                                            <li class=" dropdown-item" style="margin-left: 0.7vw;">
                                                <router-link :to="{ name: menu.routeName }">{{ menu.name }}
                                                </router-link>
                                            </li>
                                        </template>
                                    </template>
                                </ul>
                            </div>
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
    background-color: rgba(0, 0, 0, 1)
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
