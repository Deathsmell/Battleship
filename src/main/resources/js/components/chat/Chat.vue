<template>
  <v-container
      class="grey lighten-3"
      fluid
  >
    <!-- TODO: Add resize chat window -->
    <v-row
        class=""
        no-gutters
    >
      <v-col
          class="grey lighten-4"
          cols="4"
      >
        <!-- TODO: Choosing or add user with who i want to send message -->
        <v-btn-toggle borderless>
          <v-btn depressed icon>
            <v-icon>mdi-account-plus-outline</v-icon>
          </v-btn>
          <v-btn>
            <v-icon>mdi-account-circle</v-icon>
          </v-btn>
        </v-btn-toggle>
      </v-col>
      <v-col
          cols="2"
      >
        <v-btn-toggle>
          <v-btn
              disabled
              @click=""
          >
            <v-icon>mdi-account</v-icon>
          </v-btn>
          <v-btn
              @click="exitRoom()"
          >
            <v-icon>mdi-exit-to-app</v-icon>
          </v-btn>
        </v-btn-toggle>
      </v-col>
      <v-col
          cols="6"
          class="grey lighten-4"
      >
        <v-container>
          room id: {{ roomId}}
          name: {{ name }}
        </v-container>
        <v-sheet
            class="white"
        />

      </v-col>
    </v-row>

    <v-row
        no-gutters
        class=""
    >
      <v-col

      >
        <v-sheet
            height="200"
            class="overflow-y-auto"
            id="chat-window"
            ref="chatWindow"
        >
          <v-row
              no-gutters
              v-for="(message,index) in messages"
              :key="message.sender + '-' + index"
              :justify="message.sender !== name ? 'start' : 'end'"
          >
            <v-chip
                class="ma-1"
            >
              {{ message.content }}
            </v-chip>
          </v-row>
        </v-sheet>
      </v-col>
    </v-row>

    <v-row
        no-gutters
        class=""
    >
      <v-col
      >
        <v-textarea
            v-model="message.content"
            auto-grow
            filled
            single-line
            rows="1"
            prepend-inner-icon="mdi-comment"
            background-color="white"
            placeholder="Text here..."
            @keyup.enter="sendMessage(message)"
        />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import {addHandler, sendMessageInChat, unsubscribe} from "@util/ws"
import {scrollToBottom} from "@util/scroll"
import roomsApi from "@api/rooms"


export default {
  name: "Chat",

  props: {
    roomId: {
      type: String,
      required: false,
    }
  },

  data() {
    return {
      // TODO: create computed method and import mapState('message',['messages'])
      messages: [],
      // TODO: rewrite object on new ChatMessage()
      message: {
        sender: '',
        content: '',
        type: 'CHAT',
      },
    }
  },

  watch: {
    messages: () => {
      scrollToBottom()
    },
  },

  computed: {
    name: {
      get() {
        return this.$store.state.user.name
      }
    }
  },

  created() {
    console.log("SUBSCRIBE MESSAGES RECEIVE")
    addHandler(data => {
      this.messages.push(data)
    })
  },

  methods: {

    exitRoom() {
      // TODO: rewrite variable on this.room
      unsubscribe(this.$route.params.id)

      const uuid = this.$route.params.id;
      const room = roomsApi.getRoomByUUID(uuid)
      this.$store.commit("rooms/updateRoom", room)

      this.$router.push({path: '/'})
    },

    sendMessage(message) {
      message.sender = this.name

      console.log(this.messages)

      if (this.roomId !== undefined) {
        sendMessageInChat(message, this.roomId)
      } else {
        sendMessageInChat(message, null)
      }
      this.message.content = ''
    },

  }
}
</script>

<style scoped>

</style>