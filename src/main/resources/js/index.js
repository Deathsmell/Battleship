import Vue from 'vue'
import App from "./pages/App.vue";
import VueResource from 'vue-resource'
import vuetify from 'plugins/Vuetify.js'
import {connect} from "./util/ws";

Vue.use(VueResource);

connect()

new Vue({
    vuetify,
    render: a => a(App)
}).$mount('#app')
