<template>
  <v-container fluid>
    <table>
      <tr>
        <th
            v-for="y in fields.length + 1"
        >
          {{ y - 1 }}
        </th>
      </tr>
      <tr
          v-for="i in fields.length"
      >
        <td class="coordinate">{{ i }}</td>
        <td
            v-for="f in fields[i - 1].length"
            :class="'field' + ' ' + whatColor(i,f)"
            :id="i+'-'+f"
            @click="isShip(i,f)"
        >
          {{ fields[i - 1][f - 1] }}
        </td>
      </tr>
    </table>
    <v-container
        v-if="checkButton"
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


import {API} from "../util/common";

export default {
  name: "CommonDesk",

  props: {

    desk: {
      type: Array,
      required: true,
    },

    clickByField: {
      type: Function,
    },

    checkButton: {
      type: Boolean,
      default() {
        return false
      },
    }

  },

  data() {
    return {
      fields: this.desk,

      // TODO: replace in snackbar component
      snackbar: false,
      text: 'Error',
      color: 'error',
    }
  },

  methods: {
    whatColor(array, index) {
      const field = this.fields[array - 1][index - 1];

      switch (field) {
        case 0:
          return 'void'
        case 1:
          return 'ship'
        case 2:
          return 'enemy'
      }

      // if (field === 0) {
      // } else if (field === 1) {
      //   return 'ship'
      // } else if (field === 2) {
      //   return 'enemy'
      // }

    },

    isShip(array, index) {
      this.$emit('click-by-field', array, index)
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
            console.log(error);
            this.snackbar = true;
            this.text = error.status
            this.color = 'error'
          })
    },

  }
}
</script>

<style lang="sass" scoped>

table, th, td
  border: 1px solid black

  th, td
    height: 25px
    width: 25px
    text-align: center

  .coordinate
    font-weight: bold

  .field
    font: 0 Arial

  .void
    background: #1565C0


  .ship
    background: #37474F


  .enemy
    background: red

</style>