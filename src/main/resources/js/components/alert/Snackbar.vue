<template>
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
</template>

<script>
export default {
  name: "Snackbar",
  props: {
    text:{
      type: String,
      default(){
        return 'EMPTY'
      },
      required: true
    },
    color: {
      type: String,
      default() {
        return "error";
      },
      required: true
    },
    snackbar:{
      type: Boolean,
      default(){
        return false
      },
      required: true
    }
  },
  data() {
    return {
      // FIXME: Make this component universal and flexible by app
    }
  }
}
</script>

<style scoped>

</style>