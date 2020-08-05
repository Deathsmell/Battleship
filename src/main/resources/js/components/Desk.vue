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


export default {
  name: "CommonDesk",

  props: {

    desk: {
      type: Array,
      required: true,
    },

    clickByField: {
      type: Function,
      required: false,
    },

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