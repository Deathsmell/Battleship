export interface RoomInterface {
    id?: number | string,
    room?: string,
    host?: string,
    opponent?: string,
    roomStatus?: string,
    createTime?: string | Date
}

export class Room implements RoomInterface {
    private _createTime: string | Date;
    private _host: string;
    private _id: number | string;
    private _opponent: string;
    private _room: string;
    private _roomStatus: string;


    constructor(obj?: RoomInterface) {
        this._createTime = obj.createTime;
        this._host = obj.host;
        this._id = obj.id;
        this._opponent = obj.opponent;
        this._room = obj.room;
        this._roomStatus = obj.roomStatus;
    }


    get createTime(): string | Date {
        return this._createTime;
    }

    set createTime(value: string | Date) {
        this._createTime = value;
    }

    get host(): string {
        return this._host === null ? '< none >' : this._host;
    }

    set host(value: string) {
        this._host = value;
    }

    get id(): number | string {
        return this._id;
    }

    set id(value: number | string) {
        this._id = value;
    }

    get opponent(): string {
        return this._opponent === null ? '< none >' : this._opponent;
    }

    set opponent(value: string) {
        this._opponent = value;
    }

    get room(): string {
        return this._room;
    }

    set room(value: string) {
        this._room = value;
    }

    get roomStatus(): string {
        return this._roomStatus;
    }

    set roomStatus(value: string) {
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
