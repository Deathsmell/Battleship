import Vue from 'vue'
import VueRouter from 'vue-router'
import BattleField from "../pages/BattleField.vue";
import Chat from "../components/chat/Chat.vue";
import Test from "../pages/Test.vue";
import WelcomePage from "../pages/WelcomePage.vue";


Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        component: WelcomePage
    },
    {   path: '/room/:name/:id',
        name:'room',
        component: BattleField,
    },


    {
        path: '/room',
        component: BattleField,

    },
    {path: '/test', name: 'test', component: Test},
    {path: '/chat/:id', component: Chat},
    {path: '*', component: BattleField},
]

export default new VueRouter({
    mode: 'history',
    routes
})