package by.deathsmell.battleship.listener;

import org.springframework.context.event.EventListener;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

public interface WebSocketEventListener {
    @EventListener
    void handleWebSocketSubscribeListener(SessionSubscribeEvent event);

    @EventListener
    void handleWebSocketConnectListener(SessionConnectedEvent event);

    @EventListener
    void handleWebSocketUnsubscribeListener(SessionUnsubscribeEvent event);

    @EventListener
    void handleWebSocketDisconnectListener(SessionDisconnectEvent event);
}
