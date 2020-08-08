//package by.deathsmell.battleship.converter;
//
//import by.deathsmell.battleship.domain.Room;
//import by.deathsmell.battleship.dto.ChatMessage;
//import by.deathsmell.battleship.repositories.RoomRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.MethodParameter;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.support.WebDataBinderFactory;
//import org.springframework.web.context.request.NativeWebRequest;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.method.support.ModelAndViewContainer;
//
//import java.util.UUID;
//
//// TODO: Understand how this work and why this need
//@Deprecated
//public final class ChatMessageHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
//
//
//    private RoomRepository roomRepository;
//
//    @Autowired
//    public void setRoomRepository(RoomRepository roomRepository) {
//        this.roomRepository = roomRepository;
//    }
//
//    @Override
//    public boolean supportsParameter(MethodParameter parameter) {
//        return parameter.getParameterType().equals(ChatMessage.class);
//    }
//
//    @Override
//    public Object resolveArgument(MethodParameter parameter,
//                                  ModelAndViewContainer mavContainer,
//                                  NativeWebRequest webRequest,
//                                  WebDataBinderFactory binderFactory) {
//
//        String type = webRequest.getParameter("type");
//        String sender = webRequest.getParameter("sender");
//        String content = webRequest.getParameter("content");
//        String room = webRequest.getParameter("room");
//        Room roomFromDb = null;
//        if (room != null && roomRepository != null) {
//            roomFromDb = roomRepository.findByRoom(UUID.fromString(room));
//        }
//
//        return new ChatMessage(null, ChatMessage.MessageType.valueOf(type), content, sender, roomFromDb);
//    }
//}
