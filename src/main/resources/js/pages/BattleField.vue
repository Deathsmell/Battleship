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
      <chat
          :room-id="this.$route.params.id"
      />
    </v-row>
  </v-container>
</template>

<script>
import opponent from "@component/desk/EnemyDesk.vue"
import yourDesk from "@component/desk/YourDesk.vue"
import StatWindow from "@component/statistic/StatWindow.vue"
import Chat from "@component/chat/Chat.vue";

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