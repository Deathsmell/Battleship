<template>
  <v-container fluid>

    <!--TODO: create separate common component RoomButton.vue-->
    <v-row
    >
      <v-col
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
              @click="createRoom"
          >
            CREATE ROOM
          </v-card>
        </v-hover>
      </v-col>

      <chat/>

    </v-row>

  </v-container>
</template>

<script>
import {joinToRoom} from '@util/ws'
import Chat from "@component/chat/Chat.vue";
import roomApi from '@api/rooms'


export default {
  name: "WelcomePage",
  components: {
    Chat,
  },
  data() {
    return {
      room: {}
    }
  },

  computed: {
    name: {
      get() {
        return this.$store.state.user.name
      },
    }
  },

  methods: {

    moveToRoomList() {
      this.$router.push({path: `/list`})
    },

    async createRoom() {
      try {
        const respCreate = await roomApi.createRoom();
        this.room = respCreate.data
        const respJoin = await roomApi.joinRoom(this.room.room);
        if (respJoin.status === 200) {
          console.log("start joining")
          await joinToRoom(this.room.room)
          await this.$router.push({path: `room/${this.name}/${this.room.room}`})
        }
      } catch (error) {
        console.error(error.body);
      }
    }
  }
}
</script>

<style scoped>

</style>