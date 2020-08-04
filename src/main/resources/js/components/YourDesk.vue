<template>
  <v-container fluid>
    <desk
        check-button
        :desk="fields"
        @click-by-field="isShip"
    />
  </v-container>
</template>

<script>

import {API, emptyDesk} from "../util/common";
import Desk from "./Desk.vue";

export default {
  name: "BattleField",
  components: {
    Desk,
  },
  data() {
    return {
      fields: emptyDesk(),
    }
  },

  methods: {
    // TODO: Remove later
    getField() {
      this.$http.get(API + 'field').then(res => {
        res.body.forEach((array, index) => {
          this.fields.splice(index, 1, array)
        })
      })
    },

    isShip(array, index) {
      let temp = this.fields[array - 1][index - 1]
      temp = temp === 0 ? 1 : 0
      this.fields[array - 1].splice(index - 1, 1, temp)
    },
  }
}
</script>

<style lang="sass" scoped>


</style>