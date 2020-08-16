<template>
  <v-app>
    <v-main>
      <tool-bar/>
      <router-view></router-view>
    </v-main>
  </v-app>
</template>

<script>

import ToolBar from "../components/bar/ToolBar";

export default {
  name: "App",
  components: {ToolBar},
  data: () => ({}),
}
</script>

<style scoped>

</style>