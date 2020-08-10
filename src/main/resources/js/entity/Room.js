export default class Room {
    constructor(room,
                id,
                host,
                opponent,
                roomStatus,
                createTime) {
        this.room = room === null ? '' : room
        this.id = id === null ? '' : id
        this.host = host === null ? '< none >' : host
        this.opponent = opponent === null ? '< none >' : opponent
        this.roomStatus = roomStatus === undefined ? '' : roomStatus
        this.createTime = createTime === null ? '' : createTime
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

    get host() {
        return this._host;
    }

    set host(value) {
        this._host = value;
    }

    get opponent() {
        return this._opponent;
    }

    set opponent(value) {
        this._opponent = value;
    }

    get roomStatus() {
        return this._roomStatus;
    }

    set roomStatus(value) {
        this._roomStatus = value;
    }

    get createTime() {
        return this._createTime;
    }

    set createTime(value) {
        this._createTime = value;
    }

    toJSON() {
        return {
            room: this._room,
            id: this._id,
            host: this._host,
            opponent: this._opponent,
            roomStatus: this._roomStatus,
            createTime: this._createTime
        }
    }
}