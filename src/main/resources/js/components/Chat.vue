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
              class="grey lighten-4"
          >
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
                  v-for="(message,index) in messages"
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
                @keyup.alt.enter="sendMessage(message)"
            />
          </v-col>

        </v-row>
    <v-textarea
        v-model="name"
        rows="1"
        single-line
        :disabled="disable"
        @keyup.ctrl.enter="connects(name)"
    />
  </v-container>
</template>

<script>
import {addHandler, connect, sendMessage} from "../util/ws";
import {scrollToBottom} from "../util/scroll";

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


      name:'',
      disable:false,
    }
  },

  watch:{
    messages : () => {
      scrollToBottom()
    },
  },

  created() {
    addHandler(data => {
      this.messages.push(data)
    })
  },

  methods: {
    connects(sender) {
      this.disable = true
      connect(sender)
    },

    // FIXME: Rename function!!
    sendMessage(message) {
      message.sender = this.name
      console.log(message);
      sendMessage(message)
      this.message.content = ''
    },

  }
}
</script>

<style scoped>

</style>