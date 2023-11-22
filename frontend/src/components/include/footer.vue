<script setup></script>

<template>
  <p class="p">
  <div>서울 강남구 테헤란로 212</div>
  <div> <a href="https://www.ssafy.com/ksp/jsp/swp/swpMain.jsp" target="_blank">역삼 멀티 캠퍼스</a>
  </div>
  <div style="margin-top: 3vh;"></div>
  <div class="right">Copyright © All rights reserved</div>
  <div style="margin-top: 3vh;">Demo by cha_cha and mushroom <a
      href="http://www.sitepoint.com/responsive-data-tables-comprehensive-list-solutions" target="_blank">See
      article</a></div>
  </p>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Edu+TAS+Beginner:wght@500&display=swap');
@import '@/assets/base.css';

.right {
  font-family: 'Edu TAS Beginner', cursive;
  font-size: 20px
}

#footer {
  max-width: 1280px;
  margin: 0 auto;
  padding: 2rem;

  font-weight: normal;
}

a,
.green {
  text-decoration: none;
  color: hsla(160, 100%, 37%, 1);
  transition: 0.4s;
}

@media (hover: hover) {
  a:hover {
    background-color: hsla(160, 100%, 37%, 0.2);
  }
}

.p {
  text-align: center;
  margin-top: 3vh;
  margin-bottom: 3vh;
  font-size: 14px;
}
</style>
