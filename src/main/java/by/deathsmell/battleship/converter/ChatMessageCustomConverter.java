//package by.deathsmell.battleship.converter;
//
//import by.deathsmell.battleship.dto.ChatMessage;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageHeaders;
//import org.springframework.messaging.converter.MessageConversionException;
//import org.springframework.messaging.converter.MessageConverter;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//public class ChatMessageCustomConverter implements MessageConverter {
//
//
//    private final ObjectMapper objectMapper;
//
//    @Autowired
//    public ChatMessageCustomConverter(ObjectMapper objectMapper) {
//        this.objectMapper = objectMapper;
//    }
//
//    @Override
//    public Object fromMessage(Message<?> message, Class<?> targetClass) {
//        if (!(message instanceof ChatMessage)) {
//            throw new MessageConversionException("Cat");
//        }
//        Long id = ((ChatMessage) message).getId();
//
//        return null;
//    }
//
//    @Override
//    public Message<?> toMessage(Object payload, MessageHeaders headers) {
//        return null;
//    }
//}
