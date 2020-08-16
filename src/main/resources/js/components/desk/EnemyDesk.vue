<template>
  <v-container fluid>
    <desk
        :desk="fields"
        @click-by-field="isShip"
    />
  </v-container>
</template>

<script>

import Desk from "./Desk.vue";
import {API, emptyDesk} from "@util/common";

export default {
  name: "OpponentDesk",
  components: {
    Desk,
  },
  data() {
    return {
      fields: emptyDesk()
    }
  },
  methods: {

    // TODO: refactor the method so when after a request to the server, know if there is an enemy ship at this place
    isShip(array, index) {
      let temp = this.fields[array - 1][index - 1]
      temp = temp === 0 ? 2 : 0
      this.fields[array - 1].splice(index - 1, 1, temp)
    },
  }
}
</script>

<style lang="sass" scoped>

</style>