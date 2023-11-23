<script setup>
import navs from "@/components/include/nav.vue";
import footers from "@/components/include/footer.vue";

import { RouterView } from "vue-router";

import { useMemberStore } from "@/stores/member";

const memberStore = useMemberStore();
</script>
<template v-if="memberStore.userInit">
    <div id="font">
        <navs />
        <RouterView />
        <footers />
    </div>
</template>

<style>
#font {
    font-family: 'NanumBarunGothic';
}
</style>
