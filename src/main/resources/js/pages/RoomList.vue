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
    import RoomCard from "../components/list/RoomCard.vue";
    import {mapGetters} from 'vuex'

    export default {
        name: "RoomList",
        components: {
            RoomCard,
        },
        data() {
            return {
                done: false,
            }
        },
        created() {
            //empty
        },
        computed: {
            rooms:{
                get(){
                    let allRooms = this.getAllRooms();
                    console.log(allRooms)
                    return allRooms
                },
                set: function (room) {
                    this.$store.commit("rooms/updateRoom",room)
                }
            },
            name:{
                get() {
                    return this.getName()

                },
                set(name){
                    this.$store.commit("user/setName",name)
                }
            }
        },
        methods: {
            ...mapGetters('rooms', [
                'getAllRooms'
            ]),
            ...mapGetters('user', [
                'getName'
            ]),
        },
    }
</script>

<style scoped>

</style>