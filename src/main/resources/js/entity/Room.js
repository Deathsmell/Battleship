export default class Room {
    constructor(room,
                id,
                player1,
                player2,
                state) {
        this.room = room === undefined ? '' : room
        this.id = id === undefined ? '' : id
        this.player1 = player1 === undefined ? '' : player1
        this.player2 = player2 === undefined ? '' : player2
        this.state = state === undefined ? '' : state
    }


    get room() {
        return this._room;
    }

    set room(value) {
        this._room = value;
    }

    get id() {
        return this._id;
    }

    set id(value) {
        this._id = value;
    }

    get player1() {
        return this._player1;
    }

    set player1(value) {
        this._player1 = value;
    }

    get player2() {
        return this._player2;
    }

    set player2(value) {
        this._player2 = value;
    }

    get state() {
        return this._state;
    }

    set state(value) {
        this._state = value;
    }

    toJSON() {
        return {
            room: this._room,
            id: this._id,
            player1: this._player1,
            player2: this._player2,
            state: this._state,
        }
    }
}