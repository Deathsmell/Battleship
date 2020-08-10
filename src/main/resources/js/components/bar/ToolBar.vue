<template>
  <v-toolbar dense>
    <v-app-bar-nav-icon></v-app-bar-nav-icon>

    <v-toolbar-title>{{getName}}</v-toolbar-title>

    <v-spacer></v-spacer>

    <v-btn icon>
      <v-icon>mdi-magnify</v-icon>
    </v-btn>

    <v-btn icon>
      <v-icon>mdi-heart</v-icon>
    </v-btn>

    <v-btn icon>
      <v-icon>mdi-dots-vertical</v-icon>
    </v-btn>
  </v-toolbar>
</template>

<script>

  import {mapGetters} from 'vuex'

export default {
  name: "ToolBar",

  data(){
    return {

    }
  },

  computed: mapGetters('user',['getName']),
}
</script>

<style scoped>

</style>