import {RoomInterface} from "./Room";

export interface ChatMessageInterface {
    type?: string,
    content?: string,
    sender?: string,
    room?: RoomInterface
}


export class ChatMessage implements ChatMessageInterface {
    private _content: string;
    private _room: RoomInterface;
    private _sender: string;
    private _type: string;


    constructor(obj?: ChatMessageInterface) {
        this._content = obj.content;
        this._room = obj.room;
        this._sender = obj.sender;
        this._type = obj.type;
    }


    get content(): string {
        return this._content;
    }

    set content(value: string) {
        this._content = value;
    }

    get room(): RoomInterface {
        return this._room;
    }

    set room(value: RoomInterface) {
        this._room = value;
    }

    get sender(): string {
        return this._sender;
    }

    set sender(value: string) {
        this._sender = value;
    }

    get type(): string {
        return this._type;
    }

    set type(value: string) {
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