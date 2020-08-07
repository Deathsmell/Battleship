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
      <chat/>
    </v-row>
  </v-container>
</template>

<script>
import opponent from "../components/desk/EnemyDesk.vue"
import yourDesk from "../components/desk/YourDesk.vue"
import StatWindow from "../components/statistic/StatWindow.vue"
import Chat from "../components/chat/Chat.vue";

export default {
  name: "BattleField",
  components: {
    Chat,
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