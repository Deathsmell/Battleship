<template>
    <v-container fluid>

        <!--TODO: Create this separate page Auth.vue-->
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

        <!--TODO: create separate common component RoomButton.vue-->
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
    import {joinToRoom} from '../util/ws'
    import Chat from "../components/chat/Chat.vue";
    import roomApi from '../API/rooms'
    import {mapActions} from 'vuex'


    export default {
        name: "WelcomePage",
        components: {
            Chat,
        },
        data() {
            return {}
        },

        computed: {
            // FIXME: if enter first symbol a show becomings false. Need
            show() {
                return this.$store.state.user.registered
            },
            name: {
                get() {
                    return this.$store.state.user.name
                },
                set(value) {
                    this.setName(value)
                }
            }
        },

        methods: {

            ...mapActions('rooms', ['getListRooms', 'addRoom', "updateRoom"]),
            ...mapActions('user', ['connectAndSetNewUser', 'setName',"setRegistration"]),


            addUser(name) {
                this.getListRooms()
                this.connectAndSetNewUser(name)
                this.setRegistration()
            },

            moveToRoomList() {
                console.log('MOVING TO ROOM LIST PAGE!')
                this.$router.push({path: `/list`})
            },

            createRoom(sender) {
                console.log("CREATING ROOM")
                roomApi.createRoomApi().then(res => {

                    const room = res.data;
                    console.log(room)
                    this.addRoom(room)

                    const roomId = room.room;
                    joinToRoom(roomId, sender)
                    this.updateRoom(room)

                    this.$router.push({path: `room/${this.name}/${roomId}`})

                })
            }

        }
    }
</script>

<style scoped>

</style>