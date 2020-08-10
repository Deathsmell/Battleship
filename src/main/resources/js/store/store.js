import Vue from 'vue';
import Vuex from 'vuex';
import rooms from "./module/rooms";
import messages from "./module/messages";
import subscribes from "./module/subscribes";
import user from "./module/user";

Vue.use(Vuex);

export default new Vuex.Store({
    modules: {
        rooms,
        messages,
        subscribes,
        user,
    }
})