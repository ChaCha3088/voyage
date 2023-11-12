<script setup>

</script>

<template>
  <div class="hello">
    <div class="logo text-center py-6 pt-10 dark:bg-gray-700">
      <h1 class="logo-font text-4xl text-center py-8 dark:text-gray-300">|
        Voyager |</h1> <!-- <img src="{% static 'img/logo.svg' %}" class="h-20 dark:hidden" alt="Flowbite Logo"/>
            <img src="{% static 'img/logo_white.png' %}" class="h-20 hidden dark:block" alt="Flowbite Logo"/> -->

    </div>
    <nav class="navbar navbar-expand-lg bg-body-tertiary sticky-top">
      <div class="container-fluid">
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarScroll"
          aria-controls="navbarScroll" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarScroll">
          <ul class="navbar-nav me-auto my-2 my-lg-0 navbar-nav-scroll" style="--bs-scroll-height: 100px">
            <li class="nav-item">
              <a class="nav-link" href="#">메인페이지</a>
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
          <div class="dropdown" style="padding-left: 2vw;">
            <a class="btn btn-secondary" href="#" role="button" id="dropdownMenuLink"
              aria-expanded="false" style="background-color: white; color: black;">
              로그인
            </a>

            <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
              <li><a class="dropdown-item" href="#">Action</a></li>
              <li><a class="dropdown-item" href="#">Another action</a></li>
              <li><hr class="dropdown-divider"></li>
              <li><a class="dropdown-item" href="#">Something else here</a></li>
            </ul>
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
}

.logo-font {
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
  padding-right: 2vw;
}
</style>