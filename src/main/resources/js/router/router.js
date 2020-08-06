import Vue from 'vue'
import VueRouter from 'vue-router'
import BattleField from "../pages/BattleField.vue";
import Chat from "../components/chat/Chat.vue";
import StatWindow from "../components/statistic/StatWindow.vue";


Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        component: BattleField,
        children: [
            {
                path: '*',
                component: Chat
            },
            ]
    },
    {path: '/stat', name: 'stat', component: StatWindow},
    {path: '/chat/:id', component: Chat},
    {path: '/room/:id', component: BattleField},
    {path: '*', component: BattleField},
]

export default new VueRouter({
    mode: 'history',
    routes // сокращённая запись для `routes: routes`
})