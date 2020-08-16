import Vue from 'vue';
import Vuex from 'vuex';
import rooms from "./module/rooms";
import messages from "./module/messages";
import subscribes from "./module/subscribes";
import user from "./module/user";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

const userState = createPersistedState({
    paths:['user'],
    })
export default new Vuex.Store({
    modules: {
        rooms,
        messages,
        subscribes,
        user,
    },
    plugins:[userState]
})