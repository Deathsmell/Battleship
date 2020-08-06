<template>
  <v-container fluid>
    <desk
        :desk="fields"
        @click-by-field="isShip"
    />
    <v-container
    >
      <v-btn
          @click="clearField()"
      >
        Очистить поле
      </v-btn>
      <v-btn
          @click="validateField()"
      >
        Проверить
      </v-btn>
    </v-container>
    <!-- TODO: make snackbar common app object -->
    <v-snackbar
        v-model="snackbar"
        bottom="bottom"
        :color="color"
        multi-line
        timeout="6000"
        top
    >
      {{ text }}
    </v-snackbar>
  </v-container>
</template>

<script>

import {API, emptyDesk} from "../../util/common";
import Desk from "./Desk.vue";
// import Snackbar from "../alerts/Snackbar.vue";

export default {
  name: "BattleField",
  components: {
    Desk,
    // Snackbar
  },
  data() {
    return {
      fields: emptyDesk(),

      // TODO: replace in snackbar component
      snackbar: false,
      text: 'Error',
      color: 'error',
    }
  },

  methods: {
    isShip(array, index) {
      let temp = this.fields[array - 1][index - 1]
      temp = temp === 0 ? 1 : 0
      this.fields[array - 1].splice(index - 1, 1, temp)
    },

    clearField() {
      let array = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
      this.fields.forEach(value => {
        value.splice(0, value.length)
        array.forEach(zero => {
          value.push(zero)
        })
      })
    },

    validateField() {
      this.$http.post(API + 'validationField', this.fields)
          .then(response => {
            this.snackbar = true;
            this.text = response.status + ' Success validation'
            this.color = 'alert'
            // console.log(snackbar + ' ' + response.status)
          })
          .catch(error => {
            console.error(error);
            this.snackbar = true;
            this.text = error.status
            this.color = 'error'
          })
    },
  }
}
</script>

<style lang="sass" scoped>


</style>