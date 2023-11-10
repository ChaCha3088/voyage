<template>
  <div>
    <navs />
    <RouterView />
    <footers />
  </div>
</template>

<script setup>
import navs from "@/components/include/nav.vue";
import footers from "@/components/include/footer.vue";

import { RouterView } from "vue-router";
</script>

<style></style>
