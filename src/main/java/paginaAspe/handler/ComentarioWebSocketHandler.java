package paginaAspe.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import paginaAspe.Model.Comentario;

import java.util.*;

@Component
public class ComentarioWebSocketHandler extends TextWebSocketHandler {

    private final Map<Long, List<WebSocketSession>> salas = new HashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Map<String, Object> msg = mapper.readValue(message.getPayload(), Map.class);
        String tipo = (String) msg.get("tipo");

        if ("unirseSala".equals(tipo)) {
            Long campaniaId = Long.valueOf(msg.get("campaniaId").toString());
            salas.computeIfAbsent(campaniaId, k -> new ArrayList<>()).add(session);
        }

        if ("nuevoComentario".equals(tipo)) {
            Long campaniaId = Long.valueOf(msg.get("campaniaId").toString());
            List<WebSocketSession> participantes = salas.getOrDefault(campaniaId, new ArrayList<>());
            String msgStr = mapper.writeValueAsString(msg);

            for (WebSocketSession s : participantes) {
                if (s.isOpen()) s.sendMessage(new TextMessage(msgStr));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        salas.values().forEach(list -> list.remove(session));
    }
    // Método público para enviar comentarios
    public void enviarComentario(Long campaniaId, Comentario comentario) throws Exception {

    // Agregar nombre del autor según su rol
    String autorNombre = comentario.getAutorId() == 0 ? "Sistema" : (
            comentario.getAutorId() >= 1000 ? "Trabajador" : "Cliente"
    );

    Map<String, Object> payload = new HashMap<>();
    payload.put("tipo", "nuevoComentario");

    Map<String, Object> comentarioMap = new HashMap<>();
    comentarioMap.put("idComentario", comentario.getIdComentario());
    comentarioMap.put("campaniaId", comentario.getCampaniaId());
    comentarioMap.put("autorId", comentario.getAutorId());
    comentarioMap.put("autor", autorNombre);   // ← 🔥 IMPORTANTE
    comentarioMap.put("mensaje", comentario.getMensaje());
    comentarioMap.put("fecha", comentario.getFecha().toString());

    payload.put("comentario", comentarioMap);

    String json = mapper.writeValueAsString(payload);

    List<WebSocketSession> participantes = salas.getOrDefault(campaniaId, new ArrayList<>());

    for (WebSocketSession s : participantes) {
        if (s.isOpen()) s.sendMessage(new TextMessage(json));
    }
}

}
