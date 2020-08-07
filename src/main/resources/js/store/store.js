import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
    modules: {
        message: {
            namespaced: true,

            state: {
                massages: [],
            },
            getters: {
            },
            mutations: {
            },
            actions: {
            }
        },

        rooms: {
            namespaced: true,

            state: {
            },

            getters: {
            },
            mutations: {

            },
            actions: {

            }
        },

        // TODO: Риализуй адекватно !
        room: {
            namespaced: true,

            state: {
                room_id: '',
                users: [],
            },
            getters: {

            },
            mutations: {

            },
            actions: {

            }
        },

        subscribes: {
            namespaced: true,

            state: {
                subscribes: [],
            },
            getters: {
                get(state,id) {
                    const result = state.subscribes.find(subscribe => subscribe.roomId === id);
                    console.log('GET '+result.subscribe);
                    return result.subscribe
                }
            },
            mutations: {
                add(state, {roomId, subscribe}) {
                    return state.subscribes.push({roomId, subscribe})
                },
                get(state, id) {
                    const result = state.subscribes.find(subscribe => subscribe.roomId === id);
                    console.log('GET '+result.subscribe);
                    return result.subscribe
                },
                remove(state, id){
                    const index = state.subscribes.findIndex(subscribe => subscribe.roomId === id);
                    state.subscribes.splice(index,1)
                }
            },
            actions:{
                add({commit},{roomId,subscribe}){
                    commit('add',{roomId,subscribe})
                },
                get({commit},id){
                    commit('get',id)
                },
                remove({commit},id){
                    commit('remove',id)
                },
            }
        }
    }
})