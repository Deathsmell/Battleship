<template>
  <v-app>
    <battle-field/>
    <chat/>
  </v-app>
</template>

<script>
import BattleField from "../pages/BattleField.vue";
import YouDesk from "../Components/YouDesk.vue";
import Chat from "../Components/Chat.vue";

export default {
  name: "App",
  components: {
    BattleField,
    YouDesk,
    Chat,
  },
  data: () => ({}),
}
</script>

<style scoped>

</style>