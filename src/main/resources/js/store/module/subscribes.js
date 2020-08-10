export default {
    namespaced: true,

    state: {
        subscribes: [],
    },
    getters: {
        getSubscription(state, id) {
            const result = state.subscribes.find(subscribe => subscribe.roomId === id);
            console.log('GET ' + result.subscribe);
            return result.subscribe
        }
    },
    mutations: {
        addSubscription(state, {roomId, subscribe}) {
            return state.subscribes.push({roomId, subscribe})
        },
        getSubscription(state, id) {
            const result = state.subscribes.find(subscribe => subscribe.roomId === id);
            console.log('GET ' + result.subscribe);
            return result.subscribe
        },
        removeSubscription(state, id) {
            const index = state.subscribes.findIndex(subscribe => subscribe.roomId === id);
            state.subscribes.splice(index, 1)
        }
    },
    actions: {
        addSubscription({commit}, {roomId, subscribe}) {
            commit('addSubscription', {roomId, subscribe})
        },
        getSubscription({commit}, id) {
            commit('getSubscription', id)
        },
        removeSubscription({commit}, id) {
            commit('removeSubscription', id)
        },
    }
}