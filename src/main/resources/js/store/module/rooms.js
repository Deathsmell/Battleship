import roomsApi from "../../API/rooms";
import Room from "../../entity/Room";

export default {
    namespaced: true,

    state: {
        rooms: [],
    },

    getters: {
        getAllRooms(state) {
            console.log("Start getter")
            return (state.rooms || []).sort((a, b) => {
                if (a.id < b.id) {
                    return 1
                }
                if (a.id > b.id) {
                    return -1
                }
                return 0
            })
        },
        getActualRoom(state) {
            return state.rooms.filter(rooms => {
                return rooms.status === 'WAIT'
            }).sort((a, b) => {
                if (a.id < b.id) {
                    return 1
                }
                if (a.id > b.id) {
                    return -1
                }
                return 0
            })
        },
        getRoomById(state, id) {
            return state.rooms.find(room => room.id === id)
        },
        getRoomByUUID: (state) => uuid => {
            console.log("STARTING FIND ROOM BY UUID")
            console.log(uuid);
            const index = state.rooms.findIndex(room => room.room === uuid);
            console.log(index);
            return state.rooms[index] || ''

        },

    },
    mutations: {
        addRoom(state, room) {
            state.rooms = [
                ...state.rooms,
                room
            ]
        },
        updateRoom(state, room) {
            const index = state.rooms.findIndex(value => value.id === room.id);
            if (index > 2) {
                state.rooms = [
                    state.rooms.slice(0, index),
                    room,
                    state.rooms.slice(index + 1)
                ]
            }
        },
        removeRoom(state, room) {
            const index = state.rooms.findIndex(value => value.id === room.id);
            state.rooms = [
                state.rooms.slice(0, index),
                state.rooms.slice(index + 1)
            ]
        },
    },
    actions: {
        getListRooms({commit}) {
            console.log("Get list")
            roomsApi.getListRoomsApi().then(res => {
                    res.body.forEach(room => {
                        const newRoom = new Room(
                            room.room,
                            room.id,
                            room.host,
                            room.opponent,
                            room.roomStatus,
                            room.createTime
                        );
                        console.log(newRoom);
                        commit('addRoom', newRoom)
                    })
                }
            )
        },
        addRoom({commit}, room) {
            commit('addRoom', room)
        },
        updateRoom({commit}, room) {
            roomsApi.updateRoom(room).then(resp => {
                console.log("STARTING UPDATE")
                const respRoom = resp.data;
                console.log(resp.data)
                commit('updateRoom', respRoom)
            })
        },
        removeRoom({commit}, room) {
            commit('removeRoom', room)
        }
    }
}

