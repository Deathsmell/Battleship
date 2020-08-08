<template>
  <v-container fluid>
    <v-form
        v-if="show"
    >
      <v-text-field
          v-model="name"
          label="Name"
      ></v-text-field>
      <v-btn
          :disabled="name === ''"
          color="success"
          @click="addUser(name)"
      >THIS IS MY NAME
      </v-btn>
    </v-form>
    <v-row
    >
      <v-col
          v-if="!show"
          cols="6"
          class="d-flex justify-center align-center"
      >
        <v-hover
            v-slot:="{ hover }"
        >
          <v-card
              :elevation="hover ? 16 : 2"
              width="300"
              height="300"
              color="blue lighten-3"
              class="d-flex justify-center align-center mx-auto"
              @click="moveToRoomList"
          >
            ROOM LIST
          </v-card>
        </v-hover>
      </v-col>
      <v-col
          v-if="!show"
          cols="6"
          class="d-flex justify-center align-center"

      >
        <v-hover
            v-slot:="{ hover }"
        >
          <v-card
              :elevation="hover ? 16 : 2"
              width="300"
              height="300"
              color="red lighten-3"
              class="d-flex justify-center align-center mx-auto"
              @click="createRoom(name)"
          >
            CREATE ROOM
          </v-card>
        </v-hover>
      </v-col>

      <chat
          v-if="!show"
      ></chat>

    </v-row>

  </v-container>
</template>

<script>

import {connect, joinToRoom} from '../util/ws'
import Chat from "../components/chat/Chat.vue";
import {API} from "../util/common";
import {createNamespacedHelpers} from 'vuex'

const {mapActions} = createNamespacedHelpers('subscribes')

export default {
  name: "WelcomePage",
  components: {
    Chat,
  },
  data() {
    return {
      name: '',
      show: true,
    }
  },

  methods: {

    ...mapActions(['add']),

    addUser(name) {
      connect(name)
      this.show = !this.show
    },
    moveToRoomList() {
      // Empty
      console.log('MOVING TO ROOM LIST PAGE!')
    },
    createRoom(sender) {
      console.log("CREATING ROOM")
      this.$http.get(API + 'room/create').then(res => {
        const roomId = res.data.room;
        console.log(roomId)
        joinToRoom(roomId, sender)
        console.log("JOINING ROOM. Sender " + sender + " room id: " + roomId)
        this.$router.push({path: `room/${this.name}/${roomId}`})
      })
    }

  }
}
</script>

<style scoped>

</style>