<template>
  <v-container>
    <v-btn
        @click="test"
    >КНОПКА
    </v-btn>
  </v-container>
</template>

<script>
import rooms from '../API/rooms'

export default {
  name: "Test",
  methods: {
     test() {
      // const res =  resource('/room/generateRoomId').get().then(res => console.log(res.data))
       console.log(rooms.generate());
     }

  }
}
</script>

<style scoped>

</style>