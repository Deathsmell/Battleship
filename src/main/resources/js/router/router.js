import Vue from 'vue'
import VueRouter from 'vue-router'
import BattleField from "../pages/BattleField.vue";
import Chat from "../components/chat/Chat.vue";
import WelcomePage from "../pages/WelcomePage.vue";
import RoomList from "../pages/RoomList.vue";
import RoomCard from "../components/list/RoomCard.vue";
import Auth from "../pages/Auth.vue";


Vue.use(VueRouter)

const routes = [
    {
        path: '/login',
        component: Auth,
    },
    {
        path: '/',
        component: WelcomePage
    },
    {   path: '/room/:name/:id',
        name:'room',
        component: BattleField,
    },
    {
        path: '/list',
        component: RoomList,
    },
    {
        path: '/card',
        component: RoomCard
    },
    {path: '/chat/:id', component: Chat},
    {path: '*', component: BattleField},
]

export default new VueRouter({
    mode: 'history',
    routes
})