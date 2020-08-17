import SockJS from 'sockjs-client'
import {Stomp} from '@stomp/stompjs'


let stompClient = null
const handlers = []
const subscribers = []

export function connect(sender) {
    const socket = new SockJS('/room-chat')
    stompClient = Stomp.over(socket)
    stompClient.connect({sender}, frame => {
        console.log("CONNECT!!!")
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

export function sendMessageInChat(message, uuid) {
    if ('' === message.sender || null === message.sender) {
        console.error('Dont have sender header. Sender equal ' + message.sender)
    }
    if (message.type !== 'CHAT') {
        message.type = 'CHAT'
        console.error('Incorrect message type ')
    }
    if (uuid === undefined || uuid === null) {
        message.roomId = ''
        stompClient.send('/app/chat.sendMessage', {}, JSON.stringify(message))
    } else {
        if (message.content !== '') {
            stompClient.send("/app/room/" + uuid + "/chat.sendMessage", {}, JSON.stringify(message))
        } else {
            console.log('Message empty!')
        }
    }
}

export function registrationNewUser(sender) {
    stompClient.send("/app/chat.addUser", {}, JSON.stringify({sender, type: 'JOIN'}))
}

export function joinToRoom(room) {
    const subscribe = stompClient.subscribe('/topic/room/' + room,onMessageReceive)
    subscribers.push({username: room, subscribe})
}

export function subscribeGlobalChat() {
    stompClient.subscribe('/topic/public', onMessageReceive)
}

// TODO: Rewrite parameters on give Room class
export function unsubscribe(username) {
    let index = subscribers.findIndex(value => value.username = username);
    if (index > -1) {
        subscribers[index].subscribe.unsubscribe()
        subscribers.splice(index, 1)
    } else {
        console.error("Not found subscription. Room id: " + username)
        console.error(subscribers)
    }
}
