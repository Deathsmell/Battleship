<template>
  <v-container>
    <v-sheet
        height="300"
        width="300"
    >
      room id: {{ roomId }}
      <br/>
      url id: {{ this.$route.params.id}}
      <v-btn
          :value="roomId"
          @click="connects(name,roomId)"
      ></v-btn>
    </v-sheet>
  </v-container>
</template>

<script>
import {connect} from "@util/ws";

export default {
  name: "StatWindow",

  data(){
    return {
      roomId:'',
      connects(sender,room) {
        this.disable = true
        connect(sender, room)
      },
    }
  }
}
</script>

<style scoped>

</style>