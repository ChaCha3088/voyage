<script setup></script>

<template>
  <p class="p">
  <div class="right">All rights reserved</div>
  Demo by cha_cha and mushroom <a href="http://www.sitepoint.com/responsive-data-tables-comprehensive-list-solutions"
    target="_blank">See article</a>
  </p>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Edu+TAS+Beginner:wght@500&display=swap');
@import '@/assets/base.css';

.right {
  font-family: 'Edu TAS Beginner', cursive;
  font-size: 30px
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
  padding-top: 140px;
  padding-bottom: 3vh;
  font-size: 14px;
}
</style>
