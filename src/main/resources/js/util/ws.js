import SockJS from 'sockjs-client'
import {Stomp} from '@stomp/stompjs'
import {error} from "vue-resource/src/util";


let stompClient = null
const handlers = []
const subscribers = []

export function connect(sender) {
    const socket = new SockJS('/room-chat')
    stompClient = Stomp.over(socket)
    stompClient.connect({sender}, frame => {
        console.log(frame)
        registrationNewUser(sender)
        subscribeGlobalChat()
    })
}

function onMessageReceive(resp) {
    const message = JSON.parse(resp.body)
    console.log("Message receive == " + message.type)
    console.log(message)
    if (message.type === 'CHAT') {
        handlers.forEach(handler => handler(message))
    } else if (message.type === 'JOIN') {
        console.log("JOIN METHOD!!!")
        console.log(resp.headers.subscription);
        // const subscribe = JSON.parse(resp.headers.subscription)
        subscribers.push({id: message.roomId, subscribe: resp.headers.subscription});
    }

}

export function addHandler(handler) {
    handlers.push(handler)
}

export function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect()
        console.log("Disconnected")
    } else {
        console.log("Stomp client dont exist!")
    }
}

export function sendMessageInChat(message, roomId) {
    if ('' === message.sender || null === message.sender) {
        console.error('Dont have sender header. Sender equal ' + message.sender)
    }
    if (message.type !== 'CHAT') {
        message.type = 'CHAT'
        console.error('Incorrect message type ')
    }
    if (roomId === undefined) {
        message.roomId = ''
        stompClient.send('/app/chat.sendMessage', {}, JSON.stringify(message))
    } else {
        if (message.content !== '') {
            stompClient.send("/app/room/" + roomId + "/chat.sendMessage", {}, JSON.stringify(message))
        } else {
            console.log('Message empty!')
        }
    }
}

export function registrationNewUser(sender) {
    stompClient.send("/app/chat.addUser", {}, JSON.stringify({id: 0, sender, type: 'JOIN'}))
}

export function joinToRoom(room, sender) {
    console.log("Join to room")
    stompClient.subscribe('/topic/room/' + room,onMessageReceive)
    stompClient.send('/app/room/' + room + '/join', {}, JSON.stringify({
        sender: sender,
        connect:"",
        type: 'JOIN'
    }))
}

export function subscribeGlobalChat() {
    stompClient.subscribe('/topic/public', onMessageReceive)
}

// FIXME: Dont work
export function unsubscribe(roomId) {
    console.log('roomId = ' + roomId)
    let index = subscribers.findIndex(value => value.id = roomId);
    if (index > -1) {
        stompClient.unsubscribe(subscribers[index].subscribe)
        subscribers.splice(index, 1)
    } else {
        console.error("Not found subscription. Room id: " + roomId)
        error(subscribers)
    }
}
