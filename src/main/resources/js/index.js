import Vue from 'vue'
import App from "./pages/App.vue";
import VueResource from 'vue-resource'
import vuetify from 'plugins/Vuetify.js'
import router from "./router/router";

Vue.use(VueResource);

new Vue({
    vuetify,
    router,
    render: a => a(App)
}).$mount('#app')
