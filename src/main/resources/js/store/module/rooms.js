import roomsApi from "@api/rooms";
import {Room} from "@entity/Room.ts";

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
            console.log(state.rooms)
            let filter = state.rooms.filter(rooms => {
                return rooms.roomStatus === 'WAIT'
            });
            console.log(filter);
            let sort = filter.sort((a, b) => {
                if (a.id < b.id) {
                    return 1
                }
                if (a.id > b.id) {
                    return -1
                }
                return 0
            });
            console.log(sort);
            return sort
        },
        getRoomById(state, id) {
            return state.rooms.find(room => room.id === id)
        },
        getRoomByUUID: (state) => uuid => {
            console.log("STARTING FIND ROOM BY UUID")
            console.log(uuid);
            const index = state.rooms.findIndex(room => room.room === uuid);
            console.log("inxdex = " + index);
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
                state.rooms = [...state.rooms.splice(index, 1, room)]
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
        async getListRooms({commit}) {
            console.log("GET LIST ROOMS")
            const response = await roomsApi.getListRooms()
            const rooms = response.body
            console.log(rooms);
            rooms.forEach(room => {
                const newRoom = new Room({
                    id: room.id,
                    room: room.room,
                    host: room.host,
                    opponent: room.opponent,
                    roomStatus: room.roomStatus,
                    createTime: room.createTime,
                    }
                );
                console.log(newRoom);
                commit('addRoom', newRoom)
            })
        },
        async addRoom({commit}, room) {
            commit('addRoom', room)
        },
        async updateRoom({commit}, room) {
            console.log("STARTING UPDATE")
            const response = await roomsApi.updateRoom(room)
            const updatedRoom = response.data
            console.log(updatedRoom)
            commit('updateRoom', updatedRoom)
        },
        async removeRoom({commit}, room) {
            commit('removeRoom', room)
        }
    }
}

