import Vue from 'vue'
import VueResource from 'vue-resource'
import {API} from "../util/common";

Vue.use(VueResource)

const room = Vue.resource(API + '/room/generateRoomId');

export default {
    // generate: async function () {
    //     let res = await Vue.http.get(API + '/room/generateRoomId');
    //     return res.body

    // }
}
