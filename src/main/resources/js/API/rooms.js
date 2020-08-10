import Vue from 'vue'
import VueResource from 'vue-resource'
import {API} from "../util/common";

Vue.use(VueResource)

const rooms = Vue.resource(API + 'room{/action}');

export default {
    createRoomApi: () => rooms.get({action: 'create'}),
    getListRoomsApi: () => rooms.get({action: 'list'}),
    updateRoom: (room) => rooms.update({room: room})
}
