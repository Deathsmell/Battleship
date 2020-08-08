import Room from "./Room";

export default class ChatMessage {

    constructor(type,
                content,
                sender,
                room) {
        this.room = room === undefined ? new Room() : room
        this.type = type === undefined ? '' : type
        this.content = content === undefined ? '' : content
        this.sender = sender === undefined ? '' : sender
    }

    get type() {
        return this._type;
    }

    set type(value) {
        this._type = value;
    }

    get content() {
        return this._content;
    }

    set content(value) {
        this._content = value;
    }

    get sender() {
        return this._sender;
    }

    set sender(value) {
        this._sender = value;
    }

    get room() {
        return this._room
    }

    set room(room) {
        if (room instanceof Room) {
            this._room = room
        } else {
            this._room = new Room(room)
        }
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