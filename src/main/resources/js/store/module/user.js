import {connect} from "../../util/ws";

export default {
    namespaced: true,

    state: {
        name: '',
        roomId: '',
        sessionId: '',
        registered : false
    },

    getters: {
        getName: state => {
            return state.name
        },
        isRegistered: state => {
            return state.registered
        },
        getCurrentRoomId: state => {
            return state.roomId
        },
        getCurrentSessionId: state => {
            // if (state.sessionId === document.location.href)
            return state.sessionId
        }
    },
    mutations: {
        setName (state, name){
            state.name = name
        },
        setRegistration(state){
            state.registered = true
        },
        setCurrentRoomId(state, roomId){
            state.roomId = roomId
        },
        setCurrentSessionId(state){
            state.sessionId = 'empty'
        }
    },
    actions: {
        setName({commit},name){
            commit('setName',name)
        },
        setRegistration({commit}){
            commit('setRegistration')
        },
        connectAndSetNewUser({commit},name){
            connect(name);
            commit('setName',name)
        },
        setCurrentRoomId({commit}, roomId) {
            commit('setCurrentRoomId',roomId)
        },
        setCurrentSessionId({commit}) {
            commit('setCurrentSessionId')
        }
    }
}