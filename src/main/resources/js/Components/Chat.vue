<template>
  <v-container
      class="grey lighten-3"
      fluid
  >
    <v-col
        cols="3"
    >
        <v-row
            class=""
            no-gutters
        >
          <v-col
              class="grey lighten-4"
              cols="4"
          >
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
              <v-row
                  no-gutters
                  v-for="(message,index) in messages"
                  :justify="index % 2 === 1 ? 'start' : 'end'"
              >
                <v-chip
                    class="ma-1"
                >
                  {{ message }}
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
                v-model="message"
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
    </v-col>

  </v-container>
</template>

<script>
import {addHandler, sendMessage} from "../util/ws";
import {scrollToBottom} from "../util/scroll";

export default {
  name: "Chat",

  data() {
    return {
      messages: [],
      message: '',

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
    sendMessage(message) {
      sendMessage(message)
      this.message = ''
    },

  }
}
</script>

<style scoped>

</style>