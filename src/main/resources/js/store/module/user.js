export default {
    namespaced: true,

    state: {
        name: '',
        roomId: '',
    },
    getters: {
        getName: state => {
            return state.name
        },
        getCurrentRoomId: state => {
            return state.roomId
        },
    },
    mutations: {
        setName(state, name) {
            state.name = name
        },
        setCurrentRoomId(state, roomId) {
            state.roomId = roomId
        },
    },
    actions: {
        setName({commit}, name) {
            commit('setName', name)
        },
        setCurrentRoomId({commit}, roomId) {
            commit('setCurrentRoomId', roomId)
        },
    }
}