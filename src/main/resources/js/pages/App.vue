<template>
  <v-app>
    <battle-field/>
    <v-col cols="6">
      <chat/>
    </v-col>
  </v-app>
</template>

<script>
import BattleField from "../pages/BattleField.vue";
import Chat from "../components/Chat.vue";

export default {
  name: "App",
  components: {
    BattleField,
    Chat,
  },
  data: () => ({}),
}
</script>

<style scoped>

</style>