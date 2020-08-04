<template>
  <v-container fluid>
    <!-- TODO: crate common table component -->
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
            v-bind:class="{'field-void': whatColor(i,f), 'field-ship': !whatColor(i,f) }"
            v-bind:id="i+'-'+f"
            @click="isShip(i,f)"
        >
          {{ fields[i - 1][f - 1] }}
        </td>
      </tr>
    </table>
    <div
        class="mt-3"
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
    </div>
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

import {API, emptyField} from "../util/common";

export default {
  name: "BattleField",

  data() {
    return {
      fields: emptyField,

      // TODO: replace in snackbar component
      snackbar: false,
      text: 'Error',
      color: 'error',

    }
  },

  methods: {
    getField() {
      this.$http.get(API + 'field').then(res => {
        res.body.forEach((array, index) => {
          this.fields.splice(index, 1, array)
        })
      })
    },
    /* TODO: Need to make chose behavior based table type */
    whatColor(array, index) {
      return this.fields[array - 1][index - 1] === 0
    },
    /* TODO: I think this function can to make replaceable */
    isShip(array, index) {
      let temp = this.fields[array - 1][index - 1]
      temp = temp === 0 ? 1 : 0
      this.fields[array - 1].splice(index - 1, 1, temp)
    },
    /* TODO: Make disabled */
    clearField() {
      let array = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
      this.fields.forEach(value => {
        value.splice(0, value.length)
        array.forEach(zero => {
          value.push(zero)
        })
      })
    },
    /* TODO: Make disabled*/
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
    }
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


  .field-void
    background: #1565C0
    font: 0 Arial


  .field-ship
    background: #37474F
    font: 0 Arial

</style>