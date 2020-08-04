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
          v-if="buttons"
          @click="clearField()"
      >
        Очистить поле
      </v-btn>
      <v-btn
          v-if="buttons"
          @click="validateField()"
      >
        Проверить
      </v-btn>
    </div>
  </v-container>
</template>

<script>

import {API, emptyField} from "../util/common";

export default {
  name: "CommonDesk",

  props: {
    clickByField: {
      type: Function,
      default: function (){
      },
      required: true,
    },

    fieldColor: {
      type: Function,
      default: function (array,index){
        return this.fields[array - 1][index - 1] === 0
      },
      required: true,
    },

    checkButtons:{
      type: Boolean,
      default: function (){
        return false
      },
      required: false,
    }

  },

  data() {
    return {
      fields: emptyField,
    }
  },

  methods: {
    whatColor(array, index) {
      return this.fieldColor(array,index)
    },

    isShip(array, index) {
      this.clickByField(array,index)
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