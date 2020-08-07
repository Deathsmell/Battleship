import Vue from 'vue'
import App from "./pages/App.vue";
import vuetify from 'plugins/Vuetify.js'
import router from "./router/router";
import VueResource from 'vue-resource'
import store from "./store/store";

Vue.use(VueResource)

new Vue({
    vuetify,
    router,
    store,
    render: a => a(App)
}).$mount('#app')
