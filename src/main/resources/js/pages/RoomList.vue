<template>
    <v-container
            class="d-flex align-start"
    >
        <v-row
                justify="center"
        >
            <v-col
                    v-for="room in rooms"
                    :key="'room-list-col-'+room.id"
                    cols="12"
                    sm="6"
            >
                <room-card
                        :room-id="room.id"
                        :roomStatus="room.roomStatus"
                        :host="room.host"
                        :opponent="room.opponent"
                        :time="room.time"
                        :room="room.room"
                        :name="name"
                />
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
    import RoomCard from "../components/list/RoomCard.vue";
    import {API} from "../util/common";
    import Room from "../entity/Room";

    export default {
        name: "RoomList",
        components: {
            RoomCard,
        },
        data() {
            return {
                rooms: [],
                name: 'DWD',
                done: false,
            }
        },
        created() {
            this.getRooms()
        },
        methods: {

            getRooms() {
                this.$http.get(API + '/room/list').then(res => {
                    res.body.forEach(room => {
                        console.log(room);
                        const newRoom = new Room(
                            room.room,
                            room.id,
                            room.host,
                            room.opponent,
                            room.roomStatus,
                            room.createTime
                        );
                        console.log(newRoom);
                        if (this.rooms.indexOf(room) === -1){
                            this.rooms.push(newRoom)
                        }
                    })
                })
            }
        }
    }
</script>

<style scoped>

</style>