import SockJS from 'sockjs-client'
import {Stomp} from '@stomp/stompjs'


let stompClient = null
const handlers = []

export function connect(sender) {
    const socket = new SockJS('/room-chat')
    stompClient = Stomp.over(socket)
    stompClient.connect({}, frame => {
        console.log('Connected: ' + frame)
        let onMessageReceive = payload => {
            let message = JSON.parse(payload.body);
            if (message.type === 'CHAT') {
                handlers.forEach(handler => handler(message))
            }
        };
        stompClient.subscribe('/topic/public', onMessageReceive)
        stompClient.send('/app/chat.addUser', {}, JSON.stringify({sender: sender, type: 'JOIN'}))
    })
}

export function addHandler(handler) {
    handlers.push(handler)
}

export function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect()
    }
    console.log("Disconnected")
}

export function sendMessage(message) {
    if ('' === message.sender || null === message.sender) {
        console.error('Dont have sender header. Sender equal ' + message.sender)
    }
    if (message.type !== 'CHAT') {
        message.type = 'CHAT'
        console.error('Incorrect message type ')
    }
    if (message.content !== '') {
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(message))
    } else {
        console.log('Message empty!')
    }
}

export function addUser() {
    stompClient.send("/app/chat.addUser", {}, JSON.stringify({sender: "John"}))
}