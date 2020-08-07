package by.deathsmell.battleship.configuration;

import by.deathsmell.battleship.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


//    private final RoomRepository roomRepository;

//    @Autowired
//    public WebMvcConfig(RoomRepository roomRepository) {
//        this.roomRepository = roomRepository;
//    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> customizer (){
        return container -> container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND,"/"));
    }

   /* @Bean
    public ChatMessageHandlerMethodArgumentResolver chatMessageHandlerMethodArgumentResolver(){
        ChatMessageHandlerMethodArgumentResolver chatMessageHandlerMethodArgumentResolver =
                new ChatMessageHandlerMethodArgumentResolver();
        chatMessageHandlerMethodArgumentResolver.setRoomRepository(roomRepository);
        return chatMessageHandlerMethodArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new ChatMessageHandlerMethodArgumentResolver());
    }*/
}
