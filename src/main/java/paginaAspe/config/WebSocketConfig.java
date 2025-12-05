package paginaAspe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import paginaAspe.handler.ComentarioWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ComentarioWebSocketHandler comentarioHandler;

    public WebSocketConfig(ComentarioWebSocketHandler comentarioHandler) {
        this.comentarioHandler = comentarioHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(comentarioHandler, "/ws/comentarios")
                .setAllowedOrigins("*");
    }
}
