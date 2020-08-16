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
            console.log("inxdex = "  + index);
            console.log(state.rooms[index]);
            return state.rooms[index] || ''

        },

    },
    mutations: {
        addRoom(state, room) {
            console.log("ADD ROOM")
            console.log(room)
            state.rooms = [
                ...state.rooms,
                room
            ]
        },
        updateRoom(state, room) {
            const index = state.rooms.findIndex(value => value.id === room.id);
            console.log("MUTATION UPDATE STARTING")
            console.log("Index = " + index)
            console.log(room)
            if (index > 2) {
                console.log("UPDATE SLICE")
                state.rooms = [
                    state.rooms.slice(0, index),
                    room,
                    state.rooms.slice(index + 1)
                ]
            } else if (index !== -1) {
                console.log("UPDATE SPLICE")
                console.log(state.rooms);
                state.rooms = [...state.rooms.splice(index,1,room)]
                console.log(state.rooms)
            }
            console.log("MUTATION UPDATE END")
            console.log(state.rooms);
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
            roomsApi.getListRooms().then(res => {
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

