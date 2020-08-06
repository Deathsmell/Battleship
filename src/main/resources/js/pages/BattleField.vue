<template>
  <v-container>
    <v-row justify="center" align="center">
      <v-col class="d-flex justify-center align-center">
        <your-desk></your-desk>
      </v-col>
      <v-col class="d-flex justify-start align-start mb-12 pb-5">
        <opponent></opponent>
      </v-col>
    </v-row>
    <v-row>
      <router-view/>
      <stat-window/>
    </v-row>
  </v-container>
</template>

<script>
import opponent from "../components/desk/EnemyDesk.vue"
import yourDesk from "../components/desk/YourDesk.vue"
import StatWindow from "../components/statistic/StatWindow.vue"

export default {
  name: "BattleField",
  components: {
    opponent,
    yourDesk,
    StatWindow,
  },
  data() {
    return {}
  }
}

</script>

<style scoped>

</style>