import Vue from 'vue';
import Vuex from 'vuex';
import rooms from "./rooms";
import messages from "./messages";
import subscribes from "./subscribes";

Vue.use(Vuex);

export default new Vuex.Store({
    modules: {
        rooms,
        messages,
        subscribes,
    }
})