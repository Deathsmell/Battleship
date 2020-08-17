export interface RoomInterface {
    id?: Number | String,
    room?: String,
    host?: String,
    opponent?: String,
    roomStatus?: String,
    createTime?: String | Date
}

export class Room implements RoomInterface {
    private _createTime: String | Date;
    private _host: String;
    private _id: Number | String;
    private _opponent: String;
    private _room: String;
    private _roomStatus: String;


    constructor(obj?: RoomInterface) {
        this._createTime = obj.createTime;
        this._host = obj.host;
        this._id = obj.id;
        this._opponent = obj.opponent;
        this._room = obj.room;
        this._roomStatus = obj.roomStatus;
    }


    get createTime(): String | Date {
        return this._createTime;
    }

    set createTime(value: String | Date) {
        this._createTime = value;
    }

    get host(): String {
        return this._host === null ? '< none >' : this._host;
    }

    set host(value: String) {
        this._host = value;
    }

    get id(): Number | String {
        return this._id;
    }

    set id(value: Number | String) {
        this._id = value;
    }

    get opponent(): String {
        return this._opponent === null ? '< none >' : this._opponent;
    }

    set opponent(value: String) {
        this._opponent = value;
    }

    get room(): String {
        return this._room;
    }

    set room(value: String) {
        this._room = value;
    }

    get roomStatus(): String {
        return this._roomStatus;
    }

    set roomStatus(value: String) {
        this._roomStatus = value;
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
