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
          cols="1"
      >
        <v-btn-toggle>
          <v-btn
              disabled
              @click=""
          >
            <v-icon>mdi-account</v-icon>
          </v-btn>
          <v-btn
              @click="exitRoom(name)"
          >
            <v-icon>mdi-exit-to-app</v-icon>
          </v-btn>
        </v-btn-toggle>
      </v-col>
      <v-col
          cols="7"
          class="grey lighten-4"
      >
        <v-container>
          room id: {{ roomId }}
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
          <!--TODO rebuild justify env. When receive new message chek username and chose side left or right -->
          <v-row
              no-gutters
              v-for="message in messages"
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
            @keyup.alt.enter="sendMessage(message,roomId)"
        />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import {addHandler, sendMessageInChat, unsubscribe} from "../../util/ws"
import {scrollToBottom} from "../../util/scroll"
import {createNamespacedHelpers} from 'vuex'

const {mapActions,mapGetters} = createNamespacedHelpers('subscribes')


export default {
  name: "Chat",

  data() {
    return {
      messages: [],
      message: {
        sender: '',
        content: '',
        type: 'CHAT',
      },

      roomId: '',
      name: '',
      disable: false,
      subscribe: -1,
    }
  },

  watch: {
    messages: () => {
      scrollToBottom()
    },
  },

  created() {
    addHandler(data => {
      this.messages.push(data)
    })
    this.initParam()
  },

  methods: {

    ...mapActions(["remove"]),
    ...mapGetters(['get']),

    initParam() {
      this.roomId = this.$route.params.id
      this.name = this.$route.params.name
    },

    exitRoom() {
        unsubscribe(this.roomId)
    },

    sendMessage(message, roomId) {
      message.sender = this.name
      sendMessageInChat(message, roomId)
      this.message.content = ''
    },

  }
}
</script>

<style scoped>

</style>