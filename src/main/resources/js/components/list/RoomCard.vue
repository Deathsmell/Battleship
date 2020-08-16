<template>
    <v-card
            class="ma-10 grey lighten-3"
            height="200"
            width="375"
    >
        <v-row
                justify="center"
        >

            <v-col
                    style="height: 200px"
                    cols="4"
            >
                <v-col
                        class="card-title"
                >
                    <h2 style="position: relative;left: 3px">Room {{ roomId }}</h2>

                </v-col>
                <v-col
                        class="card-action"
                >
                    <v-btn
                            @click="joinInRoom"
                    >
                        Join
                    </v-btn>
                </v-col>
            </v-col>
            <v-col
                    cols="7"
                    class="d-flex justify-end align-center"
            >
                <v-container fluid>
                    <v-row>INFORMATION:</v-row>

                    <v-row
                            class="ma-1"
                    >Status:
                        <v-chip
                                class="ml-3"
                                small
                                :color="statusColor()"
                        >
                            {{ roomStatus }}
                        </v-chip>
                    </v-row>
                    <v-row
                            class="ma-1"
                    >Time create:
                        <v-chip
                                class="ml-3"
                                small
                                label
                                :color="chipColor"
                        >
                            {{ createTime }}
                        </v-chip>
                    </v-row>
                    <v-row
                            class="ma-1"
                    >Host:
                        <v-chip
                                class="ml-3"
                                small
                                label
                                :color="chipColor"
                        >
                            {{ host }}
                        </v-chip>
                    </v-row>
                    <v-row
                            class="ma-1"
                    >Opponent:
                        <v-chip
                                class="ml-3"
                                small
                                label
                                :color="chipColor"
                        >
                            {{ opponent }}
                        </v-chip>
                    </v-row>
                </v-container>

            </v-col>

        </v-row>

    </v-card>

</template>

<script>
    import {joinToRoom} from "@util/ws";
    import {mapGetters} from "vuex"
    import roomsApi from "../../API/rooms"

    export default {
        name: "RoomCard",

        props: {
            roomId: {
                type: Number,
                required: true,
            },
            roomStatus: {
                required: true,
            },
            host: {
                type: String,
                required: true,
            },
            opponent: {
                type: String,
                required: true,
            },
            createTime: {
                type: String,
                required: true,
            },
            room: {
                type: String,
                required: true,
            },
            name: {
                type: String,
                required: true,
            },
            chipColor: {
                type: String,
                default() {
                    return 'blue lighten-4'
                },
                required: false,
            }
        },

        computed: {
            join() {
                return this.roomStatus !== "WAIT"
            }
        },
        data() {
            return {}
        },

        methods: {

            ...mapGetters('user', ['getName']),

            joinInRoom() {
                roomsApi.joinRoom(this.room).then(res =>{
                    joinToRoom(this.room)
                    this.$router.push({path: `room/${this.getName()}/${this.room}`})
                }).catch(error => {
                    console.log(error);
                })
            },

            statusColor() {
                switch (this.roomStatus) {
                    case "CREATE":
                        return 'blue lighten-4'
                    case "WAIT":
                        return 'green lighten-4'
                    case "FILED":
                        this.join = !this.join
                        return 'red lighten-4'
                    default:
                        this.join = !this.join
                        return 'grey lighten-2'
                }
            },

        }
    }
</script>

<style lang="sass" scoped>

    .card-title
        position: sticky

        .card-action
            position: sticky
            top: 30%


</style>