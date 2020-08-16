import Vue from 'vue'
import VueResource from 'vue-resource'
import {API} from "@util/common";

Vue.use(VueResource)

const rooms = Vue.resource(API + 'room{/action}');

export default {
    createRoom: () => rooms.get({action: 'create'}),
    getListRooms: () => rooms.get({action: 'allRooms'}),
    joinRoom: (room) => rooms.get({action: 'join', room}),
    getRoomByUUID: (uuid) => rooms.get({action: 'get', uuid}),

    // TODO: Needed it ?
    updateRoom: (room) => rooms.update({room: room}),
}
