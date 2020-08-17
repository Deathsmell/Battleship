import {RoomInterface} from "./Room";

export interface ChatMessageInterface {
    type?: String,
    content?: String,
    sender?: String,
    room?: RoomInterface
}

export class ChatMessage implements ChatMessageInterface {
    private _content: String;
    private _room: RoomInterface;
    private _sender: String;
    private _type: String;


    constructor(obj?: ChatMessageInterface) {
        this._content = obj.content;
        this._room = obj.room;
        this._sender = obj.sender;
        this._type = obj.type;
    }


    get content(): String {
        return this._content;
    }

    set content(value: String) {
        this._content = value;
    }

    get room(): RoomInterface {
        return this._room;
    }

    set room(value: RoomInterface) {
        this._room = value;
    }

    get sender(): String {
        return this._sender;
    }

    set sender(value: String) {
        this._sender = value;
    }

    get type(): String {
        return this._type;
    }

    set type(value: String) {
        this._type = value;
    }

    toJSON() {
        return {
            type: this._type,
            content: this._content,
            sender: this._sender,
            room: this._room
        }
    }
}