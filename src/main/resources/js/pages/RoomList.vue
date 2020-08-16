<template>
    <v-container
            class="d-flex align-start"
    >
        <v-row
                justify="center"
        >
            <v-col
                    v-for="(room,index) in rooms"
                    :key="'room-list-col-'+index"
                    cols="12"
                    sm="6"
            >
                <room-card
                        :room-id="room.id"
                        :roomStatus="room.roomStatus"
                        :host="room.host"
                        :opponent="room.opponent"
                        :createTime="room.createTime"
                        :room="room.room"
                        :name="name"
                />
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
    import RoomCard from "@component/list/RoomCard.vue";
    import {mapGetters, mapActions} from 'vuex'

    export default {
        name: "RoomList",
        components: {
            RoomCard,
        },
        data() {
            return {
                //empty
            }
        },
        created() {
            this.getListRooms()
        },
        computed: {
            rooms: {
                get() {
                    return this.getAllRooms()
                },
                set: function (room) {
                    this.updateRoom(room)
                }
            },
            name: {
                get() {
                    return this.getName()

                },
                set(name) {
                    this.setName(name)
                }
            }
        },
        methods: {
            ...mapGetters('rooms', [
                'getAllRooms',
            ]),
            ...mapGetters('user', [
                'getName'
            ]),
            ...mapActions('rooms', [
                'getListRooms',
                'updateRoom'
            ]),
            ...mapActions('user', [
                'setName'
            ])
        },
    }
</script>

<style scoped>

</style>