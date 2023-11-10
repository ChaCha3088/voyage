<template>
  <navs />
  <mains />
  <footers />
</template>

<script>
import navs from "./components/include/nav.vue";
import mains from "./components/Main.vue"
import footers from "./components/include/footer.vue";

export default {
  name: "App",
  components: {
    navs,
    mains,
    footers,
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
