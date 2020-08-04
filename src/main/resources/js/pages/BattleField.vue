<template>
  <div>
    <v-row justify="center" align="center">
      <v-col class="d-flex justify-center align-center">
        <your-desk></your-desk>
      </v-col>
      <v-col class="d-flex justify-start align-start mb-12">
        <opponent></opponent>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import opponent from "../components/EnemyDesk.vue"
import yourDesk from "../components/YourDesk.vue"

export default {
  name: "BattleField",
  components: {
    opponent,
    yourDesk,
  },
  data() {
    return {}
  }
}

</script>

<style scoped>

</style>